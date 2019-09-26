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
        .setCorrect(quizAnswerEntity.getCorrect())
        .setPosition(quizAnswerEntity.getPosition())
        .setUuid(quizAnswerEntity.getUuid());
  }

  @Override
  public void applyToEntity(QuizAnswerDto quizAnswerDto, QuizAnswerEntity quizAnswerEntity) {


    quizAnswerEntity
        .setContent(quizAnswerDto.getContent())
        .setCorrect(quizAnswerDto.getCorrect())
        .setPosition(quizAnswerDto.getPosition())
        .setUuid(quizAnswerDto.getUuid());
  }
}
