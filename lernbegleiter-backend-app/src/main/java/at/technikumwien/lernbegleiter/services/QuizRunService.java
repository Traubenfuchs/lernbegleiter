package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.converter.quiz.QuizRunConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import at.technikumwien.lernbegleiter.repositories.quiz.QuizRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizRunRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
        quizRunEntity.setQuiz(quizRepository.getOne(quizUUID));
        return new UuidResponse(quizRunRepository.save(quizRunEntity).getUuid());
    }

    public QuizRunDto get(@NonNull String quizRunUUID) {
        return quizRunConverter.toDTO(quizRunRepository.getOne(quizRunUUID));
    }

    public Collection<QuizRunDto> getRuns(@NonNull String quizUUID) {
        return quizRunConverter.toDtoSet(quizRunRepository.getByQuiz(quizUUID));
    }

    public void put(@NonNull @Valid QuizRunDto quizRunDto) {
        QuizRunEntity qre = quizRunRepository.getOne(quizRunDto.getUuid());
        quizRunConverter.applyToEntity(quizRunDto, qre);
    }
}
