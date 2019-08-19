package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizQuestionDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizQuestionConverter extends DtoEntityConverter<QuizQuestionEntity, QuizQuestionDto> {
    @Autowired
    private QuizAnswerConverter quizAnswerConverter;

    @Override
    public void applyToDto(QuizQuestionEntity quizQuestionEntity, QuizQuestionDto quizQuestionDto) {
        quizQuestionDto
                .setAnswers(quizAnswerConverter.toDtoSet(quizQuestionEntity.getAnswers()))
                .setContent(quizQuestionEntity.getContent())
        ;
    }

    @Override
    public void applyToEntity(QuizQuestionDto quizQuestionDto, QuizQuestionEntity quizQuestionEntity) {
        quizQuestionEntity
                //.setAnswers(quizAnswerConverter.toEntitySet(quizQuestionDto.getAnswers()))
                .setContent(quizQuestionDto.getContent())
        ;
    }
}
