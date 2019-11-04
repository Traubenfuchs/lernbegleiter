package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.time.*;

@Slf4j
@Component
public class QuizRunStatePromoter {
  @Lazy
  @Autowired
  private QuizRunStatePromoter self;

  @Autowired
  private QuizRunRepository quizRunRepository;

  @Scheduled(fixedDelay = 1000, initialDelay = 10000)
  public void scheduledClean() {
    try {
      self.promote();
    } catch (Throwable throwable) {
      log.error("Promotion of quiz run states failed.", throwable);
    }
  }

  @Transactional
  public void promote() {
    quizRunRepository
      .findByState(QuizRunState.WAITING_FOR_ANSWERS)
      .forEach(quizRunEntity -> {
        QuizQuestionEntity currentQuestion = quizRunEntity.getCurrentQuestion();
        if (!Instant.now().isAfter(quizRunEntity.getNextTimeLimit())) {
          return; // too early for quiz-run promotion //TODO only select quiz runs with time limit after now (-;
        }

        if (quizRunEntity.getQuizRunType() == QuizRunType.FREE_ANSWERING) {
          quizRunEntity
            .setState(QuizRunState.DONE)
            .setNextTimeLimit(null);
          return;
        }

        int currentPosition = currentQuestion.getPosition();
        quizRunEntity
          .setNextTimeLimit(null)
          .getQuiz()
          .getQuestions()
          .stream()
          // find next quiz question
          .filter(qq -> qq.getPosition() == currentPosition + 1)
          .findFirst()
          .ifPresentOrElse(
            qq -> {
              log.info("Promoted quiz-run with uuid<{}> to state<WAITING_FOR_NEXT_QUESTION>", quizRunEntity.getUuid());
              quizRunEntity.setState(QuizRunState.WAITING_FOR_NEXT_QUESTION);
            },
            () -> {
              log.info("Promoted quiz-run with uuid<{}> to state<DONE>", quizRunEntity.getUuid());
              quizRunEntity
                //               .setCurrentQuestion(null) //TODO leave current question with right answer
                .setState(QuizRunState.DONE);
            }
          );
      });
  }
}
