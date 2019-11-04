package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class QuizRunConverter extends DtoEntityConverter<QuizRunEntity, QuizRunDto> {
  @Autowired
  private QuizQuestionConverter quizQuestionConverter;

  @Override
  public void applyToDto(QuizRunEntity quizRunEntity, QuizRunDto quizRunDto) {
    super.applyToDto(quizRunEntity, quizRunDto);
    quizRunDto
      .setNextTimeLimit(quizRunEntity.getNextTimeLimit())
      .setState(quizRunEntity.getState())
      .setQuizRunType(quizRunEntity.getQuizRunType())
      .setCurrentQuestions(
        switch (quizRunEntity.getQuizRunType()) {
          case ONE_QUESTION_AT_A_TIME -> Set.of(quizQuestionConverter.toDTO(quizRunEntity.getCurrentQuestion()));
          case FREE_ANSWERING -> quizQuestionConverter.toDtoSet(quizRunEntity.getQuiz().getQuestions());
        }
      )
    ;
  }

  @Override
  public void applyToEntity(QuizRunDto quizRunDto, QuizRunEntity quizRunEntity) {
    quizRunEntity
      .setQuizRunType(quizRunDto.getQuizRunType())
      .setNextTimeLimit(quizRunDto.getNextTimeLimit());
  }
}
