package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class QuizRunConverter extends DtoEntityConverter<QuizRunEntity, QuizRunDto> {
  @Autowired
  private QuizQuestionConverter quizQuestionConverter;

  @Override
  public void applyToDto(QuizRunEntity quizRunEntity, QuizRunDto quizRunDto) {
    super.applyToDto(quizRunEntity, quizRunDto);
    quizRunDto
      .setCurrentQuestion(quizQuestionConverter.toDTO(quizRunEntity.getCurrentQuestion()))
      .setNextTimeLimit(quizRunEntity.getNextTimeLimit())
      .setState(quizRunEntity.getState())
      .setQuizRunType(quizRunEntity.getQuizRunType())
    ;
  }

  @Override
  public void applyToEntity(QuizRunDto quizRunDto, QuizRunEntity quizRunEntity) {
    quizRunEntity.setQuizRunType(quizRunDto.getQuizRunType());
  }
}
