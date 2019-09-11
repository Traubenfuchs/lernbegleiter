package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizQuestionDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@Component
public class QuizQuestionConverter extends DtoEntityConverter<QuizQuestionEntity, QuizQuestionDto> {
    @Autowired
    private QuizAnswerConverter quizAnswerConverter;

    @Override
    public void applyToDto(QuizQuestionEntity quizQuestionEntity, QuizQuestionDto quizQuestionDto) {
        quizQuestionDto
                .setAnswers(quizAnswerConverter.toDtoSet(quizQuestionEntity.getAnswers()))
                .setPosition(quizQuestionEntity.getPosition())
                .setContent(quizQuestionEntity.getContent())
                .setUuid(quizQuestionEntity.getUuid())
        ;
    }

    @Override
    public void applyToEntity(QuizQuestionDto quizQuestionDto, QuizQuestionEntity quizQuestionEntity) {
        quizAnswerConverter.applyOrCreateToEntityCollection(quizQuestionDto.getAnswers(), quizQuestionEntity.getAnswers());
        quizQuestionEntity.getAnswers().forEach(qa -> qa.setQuizQuestion(quizQuestionEntity));

        quizQuestionEntity
                .setPosition(quizQuestionDto.getPosition())
                .setContent(quizQuestionDto.getContent())
                .setUuid(quizQuestionDto.getUuid())
        ;
    }
}
