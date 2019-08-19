package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizAnswerDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizAnswerEntity;
import org.springframework.stereotype.Component;

@Component
public class QuizAnswerConverter extends DtoEntityConverter<QuizAnswerEntity, QuizAnswerDto> {
    @Override
    public void applyToDto(QuizAnswerEntity quizAnswerEntity, QuizAnswerDto quizAnswerDto) {
        quizAnswerDto
                .setContent(quizAnswerEntity.getContent())
                .setUuid(quizAnswerEntity.getUuid());
    }

    @Override
    public void applyToEntity(QuizAnswerDto quizAnswerDto, QuizAnswerEntity quizAnswerEntity) {
        quizAnswerEntity
                .setContent(quizAnswerDto.getContent())
                .setUuid(quizAnswerDto.getUuid());
    }
}
