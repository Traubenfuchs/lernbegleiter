package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class QuizQuestionConverter extends DtoEntityConverter<QuizQuestionEntity, QuizQuestionDto> {
  @Autowired
  private QuizAnswerConverter quizAnswerConverter;
  @Autowired
  private LobService lobService;

  @Override
  public void applyToDtoCustom(QuizQuestionEntity quizQuestionEntity, QuizQuestionDto quizQuestionDto) {
    quizQuestionDto
      .setAnswers(quizAnswerConverter.toDtoSet(quizQuestionEntity.getAnswers()))
      .setPosition(quizQuestionEntity.getPosition())
      .setContent(quizQuestionEntity.getContent())
      .setUuid(quizQuestionEntity.getUuid())
      .setTimeLimit(quizQuestionEntity.getTimeLimit())
      .setAnswerCount(quizQuestionEntity.getAnswerCount())
      .setFreeText(quizQuestionEntity.getFreeText())
      .setQuizQuestionType(quizQuestionEntity.getQuizQuestionType())
      .setLob(new QuizLobDto()
        .setQuizPictureUUID(quizQuestionEntity.getFkLobUUID()))
    ;
  }

  @Override
  public void applyToEntityCustom(QuizQuestionDto quizQuestionDto, QuizQuestionEntity quizQuestionEntity) {
    quizAnswerConverter.applyOrCreateToEntityCollection(quizQuestionDto.getAnswers(), quizQuestionEntity.getAnswers());
    quizQuestionEntity.getAnswers().forEach(qa -> qa.setQuizQuestion(quizQuestionEntity));

    lobService.applyImage(quizQuestionDto.getLob(), quizQuestionEntity);

    quizQuestionEntity
      .setPosition(quizQuestionDto.getPosition())
      .setContent(quizQuestionDto.getContent())
      .setTimeLimit(quizQuestionDto.getTimeLimit())
      .setQuizQuestionType(quizQuestionDto.getQuizQuestionType())
      .setFreeText(quizQuestionDto.getFreeText())
      .setUuid(quizQuestionDto.getUuid())
    ;
  }
}
