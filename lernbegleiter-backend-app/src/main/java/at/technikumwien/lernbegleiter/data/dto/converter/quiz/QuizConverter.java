package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.beans.factory.annotation.*;

public class QuizConverter extends DtoEntityConverter<QuizEntity, QuizDto> {
  @Autowired
  private QuizQuestionConverter quizQuestionConverter;
  @Autowired
  private QuizRunConverter quizRunConverter;
  @Autowired
  private TeacherConverter teacherConverter;

  @Override
  public void applyToDtoCustom(QuizEntity quizEntity, QuizDto quizDto) {
    quizDto
      .setUuid(quizEntity.getUuid())
      .setAuthor(teacherConverter.toDTO(quizEntity.getAuthor()))
      .setDescription(quizEntity.getDescription())
      .setMaxRetries(quizEntity.getMaxRetries())
      .setName(quizEntity.getName())
    ;
  }

  @Override
  public void applyToEntityCustom(QuizDto quizDto, QuizEntity quizEntity) {
    quizEntity
      .setUuid(quizDto.getUuid())
      // .setAuthor(quizDto.getAuthor())
      .setDescription(quizDto.getDescription())
      .setMaxRetries(quizDto.getMaxRetries())
      .setName(quizDto.getName())
    ;
  }
}
