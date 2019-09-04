package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.LobDto;
import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizQuestionDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import at.technikumwien.lernbegleiter.services.LobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizQuestionConverter extends DtoEntityConverter<QuizQuestionEntity, QuizQuestionDto> {
    @Autowired
    private QuizAnswerConverter quizAnswerConverter;
    @Autowired
    private LobService lobService;

    @Override
    public void applyToDto(QuizQuestionEntity quizQuestionEntity, QuizQuestionDto quizQuestionDto) {
        quizQuestionDto
                .setAnswers(quizAnswerConverter.toDtoSet(quizQuestionEntity.getAnswers()))
                .setPosition(quizQuestionEntity.getPosition())
                .setContent(quizQuestionEntity.getContent())
                .setUuid(quizQuestionEntity.getUuid())
                .setLob(new LobDto()
                        .setQuizPictureUUID(quizQuestionEntity.getFkLobUUID()))
        ;
    }

    @Override
    public void applyToEntity(QuizQuestionDto quizQuestionDto, QuizQuestionEntity quizQuestionEntity) {
        quizAnswerConverter.applyOrCreateToEntityCollection(quizQuestionDto.getAnswers(), quizQuestionEntity.getAnswers());
        quizQuestionEntity.getAnswers().forEach(qa -> qa.setQuizQuestion(quizQuestionEntity));

        lobService.applyImage(quizQuestionDto.getLob(), quizQuestionEntity);

        quizQuestionEntity
                .setPosition(quizQuestionDto.getPosition())
                .setContent(quizQuestionDto.getContent())
                .setUuid(quizQuestionDto.getUuid())
        ;
    }
}
