package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizConverter extends DtoEntityConverter<QuizEntity, QuizDto> {
    @Autowired
    private QuizQuestionConverter quizQuestionConverter;
    @Autowired
    private QuizRunConverter quizRunConverter;

    @Override
    public void applyToDto(QuizEntity quizEntity, QuizDto quizDto) {
        quizDto
                .setUuid(quizEntity.getUuid())
                .setAuthor(quizEntity.getAuthor())
                .setDescription(quizEntity.getDescription())
                .setExpirationDate(quizEntity.getExpirationDate())
                .setMaxRetries(quizEntity.getMaxRetries())
                .setName(quizEntity.getName())
                .setQuestions(quizQuestionConverter.toDtoSet(quizEntity.getQuestions()))
                .setQuizRuns(quizRunConverter.toDtoSet(quizEntity.getQuizRuns()))
                .setQuizType(quizEntity.getQuizType())
        ;
    }

    @Override
    public void applyToEntity(QuizDto quizDto, QuizEntity quizEntity) {
        quizQuestionConverter.applyOrCreateToEntityCollection(quizDto.getQuestions(), quizEntity.getQuestions());
        quizRunConverter.applyOrCreateToEntityCollection(quizDto.getQuizRuns(), quizEntity.getQuizRuns());

        quizEntity
                .setUuid(quizDto.getUuid())
                .setAuthor(quizDto.getAuthor())
                .setDescription(quizDto.getDescription())
                .setExpirationDate(quizDto.getExpirationDate())
                .setMaxRetries(quizDto.getMaxRetries())
                .setName(quizDto.getName())
                .setQuizType(quizDto.getQuizType())
        ;
    }
}
