package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@AllArgsConstructor
@Service
public class MyQuizzesService {
  private final QuizAttemptRepository quizAttemptRepository;

  @Transactional
  public MyQuizzesDto get() {
    Set<QuizAttemptEntity> quizAttemptEntities = quizAttemptRepository.findByFkStudentUuid(AuthHelper.getCurrentUserUUIDOrThrow());

    MyQuizzesDto result = new MyQuizzesDto();

    for (QuizAttemptEntity quizAttemptEntity : quizAttemptEntities) {
      QuizRunEntity quizRunEntity = quizAttemptEntity.getQuizRun();
      QuizEntity quizEntity = quizRunEntity.getQuiz();
      result.getEntries().add(new MyQuizzesEntryDto()
        .setName(quizEntity.getName())
        .setQuizRunUuid(quizRunEntity.getUuid())
        .setQuizUuid(quizEntity.getUuid())
        .setRunState(quizRunEntity.getState())
        .setQuizType(quizRunEntity.getQuizRunType()));
    }

    return result;
  }
}
