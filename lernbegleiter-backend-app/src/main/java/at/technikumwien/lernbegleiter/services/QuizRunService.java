package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.data.dto.converter.quiz.QuizRunConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import at.technikumwien.lernbegleiter.repositories.quiz.QuizRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizRunRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Collection;

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

    public UuidResponse post(@NonNull String quizUUID, @NonNull @Valid QuizRunDto quizRunDto) {
        QuizRunEntity quizRunEntity = quizRunConverter.toEntity(quizRunDto);
        quizRunEntity.setState(QuizRunState.CREATED);
        quizRunEntity.setQuiz(quizRepository.getOne(quizUUID));
        return new UuidResponse(quizRunRepository.save(quizRunEntity).getUuid());
    }

    public QuizRunDto get(@NonNull String quizRunUUID) {
        return quizRunConverter.toDTO(quizRunRepository.getOne(quizRunUUID));
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
        QuizQuestionEntity qqe;
        if (quizRunEntity.getState() == QuizRunState.CREATED) {
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

        quizRunEntity.setCurrentQuestion(qqe);
        quizRunEntity.setNextTimeLimit(Instant.now().plusSeconds(qqe.getTimeLimit()));
        quizRunEntity.setState(QuizRunState.WAITING_FOR_ANSWERS);
    }
}
