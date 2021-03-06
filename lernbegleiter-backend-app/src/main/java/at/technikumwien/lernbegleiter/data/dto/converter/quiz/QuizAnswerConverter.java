package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.stereotype.*;

@Component
public class QuizAnswerConverter extends DtoEntityConverter<QuizAnswerEntity, QuizAnswerDto> {
  @Override
  public void applyToDtoCustom(QuizAnswerEntity quizAnswerEntity, QuizAnswerDto quizAnswerDto) {
    quizAnswerDto
      .setContent(quizAnswerEntity.getContent())
      .setCorrect(quizAnswerEntity.getCorrect())
      .setPosition(quizAnswerEntity.getPosition())
      .setUuid(quizAnswerEntity.getUuid());
  }

  @Override
  public void applyToEntityCustom(QuizAnswerDto quizAnswerDto, QuizAnswerEntity quizAnswerEntity) {
    quizAnswerEntity
      .setContent(quizAnswerDto.getContent())
      .setCorrect(quizAnswerDto.getCorrect())
      .setPosition(quizAnswerDto.getPosition())
      .setUuid(quizAnswerDto.getUuid());
  }
}
