package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.converter.quiz.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import com.google.common.cache.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.server.*;

import javax.validation.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

@Validated
@Transactional
@Service
public class QuizRunService {
  @Autowired
  private QuizRunRepository quizRunRepository;
  @Autowired
  private QuizRunConverter quizRunConverter;
  @Autowired
  private QuizRepository quizRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;
  @Autowired
  private QuizQuestionAnswerAttemptRepository quizQuestionAnswerAttemptRepository;
  @Autowired
  private QuizAttemptService quizAttemptService;
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizQuestionRepository quizQuestionRepository;

  private final LoadingCache<String, QuizRunDto> cache;

  public QuizRunService() {
    cache = CacheBuilder
      .newBuilder()
      .maximumSize(100)
      .expireAfterWrite(1, TimeUnit.SECONDS)
      .build(new CacheLoader<>() {
        @Override
        public QuizRunDto load(String key) {
          return get(key);
        }
      });
  }

  public UuidResponse post(@NonNull String quizUUID, @NonNull @Valid QuizRunDto quizRunDto) {
    QuizEntity quizEntity = quizRepository.getOne(quizUUID);
    QuizRunEntity quizRunEntity = quizRunConverter
      .toEntity(quizRunDto)
      .setState(QuizRunState.CREATED)
      .setQuiz(quizEntity)
      .setQuestionCount(quizEntity.getQuestions().size());
    return new UuidResponse(quizRunRepository.save(quizRunEntity).getUuid());
  }

  public QuizRunDto getCachedForAdmin(@NonNull String quizRunUUID) throws ExecutionException {
    return cache.get(quizRunUUID);
  }

  public QuizRunDto getCachedForStudent(@NonNull String quizRunUUID) throws ExecutionException {
    QuizRunDto result = cache.get(quizRunUUID)
      .deepClone();

    if (result.getState() == QuizRunState.CREATED) {
      result.setCurrentQuestions(new HashSet<>());
    }

    // only while a quiz is getting answered should the correct answers be overwritten with the given answers
    // while the quiz is not answered (=after answering / while waiting for next question / after quiz) the correct answers should be shown
    quizAttemptService.enrichWithAttemptData(result);

    return result;
  }

  public QuizRunDto get(@NonNull String quizRunUUID) {
    try {
      QuizRunDto result = quizRunConverter
        .toDTO(quizRunRepository.getOne(quizRunUUID));
      return result;
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public Collection<QuizRunDto> getRuns(@NonNull String quizUUID) {
    return quizRunConverter.toDtoSet(quizRunRepository.getByFkQuizUuid(quizUUID));
  }

  public void put(@NonNull @Valid QuizRunDto quizRunDto) {
    QuizRunEntity qre = quizRunRepository.getOne(quizRunDto.getUuid());
    quizRunConverter.applyToEntity(quizRunDto, qre);
  }

  public void advance(@NonNull String quizRunUUID) {
    QuizRunEntity quizRunEntity = quizRunRepository.getOne(quizRunUUID);
    if (quizRunEntity.getQuizRunType() == QuizRunType.ONE_QUESTION_AT_A_TIME) {
      advanceOneQuestionAtATimeQuizRun(quizRunEntity);
    } else if (quizRunEntity.getQuizRunType() == QuizRunType.FREE_ANSWERING) {
      advanceFreeAnsweringQuizRun(quizRunEntity);
    }
  }

  private void advanceOneQuestionAtATimeQuizRun(QuizRunEntity quizRunEntity) {
    QuizQuestionEntity qqe;
    if (quizRunEntity.getState() == QuizRunState.CREATED) {
      quizRunEntity.setStartedAt(Instant.now());
      qqe = quizRunEntity
        .getQuiz()
        .getQuestions()
        .stream()
        .filter(question -> question.getPosition() == 0)
        .findFirst()
        .get();
    } else if (quizRunEntity.getState() == QuizRunState.WAITING_FOR_NEXT_QUESTION) {
      qqe = quizRunEntity
        .getQuiz()
        .getQuestions()
        .stream()
        .filter(question -> question.getPosition() == (quizRunEntity.getCurrentQuestion().getPosition() + 1))
        .findFirst()
        .get();
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "State <" + quizRunEntity.getState() + "> can not be advanced!");
    }

    createAttemptsForAllRuns(quizRunEntity, qqe);

    quizRunEntity
      .setCurrentQuestion(qqe)
      .setNextTimeLimit(Instant.now().plusSeconds(qqe.getTimeLimit()))
      .setState(QuizRunState.WAITING_FOR_ANSWERS);
  }

  private void advanceFreeAnsweringQuizRun(QuizRunEntity quizRunEntity) {
    if (quizRunEntity.getState() == QuizRunState.CREATED) {
      quizRunEntity.setState(QuizRunState.WAITING_FOR_ANSWERS);
    } else if (quizRunEntity.getState() == QuizRunState.WAITING_FOR_ANSWERS) {
      quizRunEntity
        .setState(QuizRunState.DONE)
        .setNextTimeLimit(null);
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "free-answering quizRun is in state" + quizRunEntity.getState() + ", can not advance that.");
    }
  }

  /**
   * Creates QuizQuestionAttempts and QuizAnswerAttempts for one QuizAttempt for all existing QuizAttempts
   *
   * @param quizRunEntity
   * @param quizQuestionEntity
   */
  private void createAttemptsForAllRuns(QuizRunEntity quizRunEntity, QuizQuestionEntity quizQuestionEntity) {
    quizRunEntity
      .getAttempts()
      .forEach(quizRunAttempt -> createQuestionAndAnswerAttemptsForRun(quizRunAttempt, quizQuestionEntity));
  }

  /**
   * Creates QuizQuestionAttempts and QuizAnswerAttempts for one QuizAttempt
   *
   * @param quizAttemptEntity
   * @param quizQuestionEntity
   */
  private void createQuestionAndAnswerAttemptsForRun(QuizAttemptEntity quizAttemptEntity, QuizQuestionEntity quizQuestionEntity) {
    QuizQuestionAttemptEntity newQuizQuestionAttemptEntity =
      quizQuestionAttemptRepository.save(
        new QuizQuestionAttemptEntity()
          .setQuizAttempt(quizAttemptEntity)
          .setQuizQuestion(quizQuestionEntity));

    quizQuestionEntity
      .getAnswers()
      .forEach(quizQuestionAnswerEntity ->
        quizQuestionAnswerAttemptRepository.save(
          new QuizQuestionAnswerAttemptEntity()
            .setCorrect(false)
            .setQuizQuestionAttempt(newQuizQuestionAttemptEntity)
            .setQuizAnswer(quizQuestionAnswerEntity)));
  }

  /**
   * Creates all QuizQuestionAttempts and QuizAnswerAttempts for one QuizAttempt
   *
   * @param quizAttemptUuid
   */
  public void createQuestionAndAnswerAttemptsForQuizAttempt(@NonNull String quizAttemptUuid) {
    QuizAttemptEntity quizAttemptEntity = quizAttemptRepository.getOne(quizAttemptUuid);
    Collection<QuizQuestionEntity> quizQuestions = quizQuestionRepository.findAllByFkQuizzUuid(quizAttemptEntity.getQuizRun().getFkQuizUuid());
    quizQuestions.forEach(quizQuestionEntity -> createQuestionAndAnswerAttemptsForRun(quizAttemptEntity, quizQuestionEntity));
  }
}
