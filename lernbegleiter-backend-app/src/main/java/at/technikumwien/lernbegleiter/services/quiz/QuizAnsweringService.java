package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.repositories.quiz.attempts.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.persistence.*;

@Transactional
@Service
public class QuizAnsweringService {
  @Autowired
  private QuizAttemptRepository quizAttemptRepository;
  @Autowired
  private QuizQuestionAttemptRepository quizQuestionAttemptRepository;
  @Autowired
  private QuizQuestionAnswerAttemptRepository quizQuestionAnswerAttemptRepository;
  @Autowired
  private EntityManager em;

  public void answer(
    @NonNull String quizAttemptUuid,
    @NonNull String quizAnswerUuid,
    @NonNull Boolean correct) {
    Query query = em.createNativeQuery("update QUIZ_QUESTION_ANSWER_ATTEMPT " +
      "SET CORRECT = ? " +
      "WHERE UUID = ( " +
      "  select qqaa.UUID " +
      "    FROM (SELECT * from QUIZ_QUESTION_ANSWER_ATTEMPT WHERE FK_QUIZ_ANSWER_UUID = ?) qqaa " +
      "    join QUIZ_QUESTION_ATTEMPT qqa " +
      "      on qqaa.FK_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID = qqa.UUID " +
      "    join QUIZ_ATTEMPT qa " +
      "      on qqa.FK_QUIZ_ATTEMPT_UUID= qa.UUID " +
      " where 1=1 " +
      "    and qqaa.FK_QUIZ_ANSWER_UUID = ? " +
      "    and qa.UUID = ? " +
      ")");
    query.setParameter(1, correct);
    query.setParameter(2, quizAnswerUuid);
    query.setParameter(3, quizAnswerUuid);
    query.setParameter(4, quizAttemptUuid);

    query.executeUpdate();
  }
}
