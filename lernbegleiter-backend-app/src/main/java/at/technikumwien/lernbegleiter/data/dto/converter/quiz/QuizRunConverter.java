package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizRunConverter extends DtoEntityConverter<QuizRunEntity, QuizRunDto> {
  @Autowired
  private QuizQuestionConverter quizQuestionConverter;

  @Override
  public void applyToDto(QuizRunEntity quizRunEntity, QuizRunDto quizRunDto) {
    quizRunDto
        .setCurrentQuestion(quizQuestionConverter.toDTO(quizRunEntity.getCurrentQuestion()))
        .setNextTimeLimit(quizRunEntity.getNextTimeLimit())
        .setState(quizRunEntity.getState())
    ;
  }

  @Override
  public void applyToEntity(QuizRunDto quizRunDto, QuizRunEntity quizRunEntity) {

  }
}
