package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.*;
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
  public void applyToDtoCustom(QuizRunEntity quizRunEntity, QuizRunDto quizRunDto) {
    quizRunDto
      .setNextTimeLimit(quizRunEntity.getNextTimeLimit())
      .setState(quizRunEntity.getState())
      .setQuizRunType(quizRunEntity.getQuizRunType())
      .setQuestionCount(quizRunEntity.getQuestionCount())
      .setCurrentQuestions(
        switch (quizRunEntity.getQuizRunType()) {
          case ONE_QUESTION_AT_A_TIME -> {
            if (quizRunEntity.getState() == QuizRunState.DONE) {
              yield quizQuestionConverter.toDtoSet(quizRunEntity.getQuiz().getQuestions());
            }
            yield quizRunEntity.getCurrentQuestion() == null ? new HashSet<>() : Set.of(quizQuestionConverter.toDTO(quizRunEntity.getCurrentQuestion()));
          }
          case FREE_ANSWERING, FINISH_SELF -> quizQuestionConverter.toDtoSet(quizRunEntity.getQuiz().getQuestions());
        }
      )
    ;
  }

  @Override
  public void applyToEntityCustom(QuizRunDto quizRunDto, QuizRunEntity quizRunEntity) {
    quizRunEntity
      .setQuizRunType(quizRunDto.getQuizRunType())
      .setNextTimeLimit(quizRunDto.getNextTimeLimit());
  }
}
