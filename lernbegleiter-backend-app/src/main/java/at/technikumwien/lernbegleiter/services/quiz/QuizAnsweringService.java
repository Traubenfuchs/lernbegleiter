package at.technikumwien.lernbegleiter.services.quiz;

import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.persistence.*;
import java.time.*;

@AllArgsConstructor
@Transactional
@Service
public class QuizAnsweringService {
  private final EntityManager em;

  public void tick(
    @NonNull String quizAttemptUuid,
    @NonNull String quizAnswerUuid,
    @NonNull Boolean correct) {
    Query query = em.createNativeQuery(
      """
              update QUIZ_QUESTION_ANSWER_ATTEMPT
              SET
                CORRECT = ?,
                ANSWERED_AT = ?
              WHERE UUID = (
                select qqaa.UUID
                  FROM (SELECT * from QUIZ_QUESTION_ANSWER_ATTEMPT WHERE FK_QUIZ_ANSWER_UUID = ?) qqaa
                    join QUIZ_QUESTION_ATTEMPT qqa
                      on qqaa.FK_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID = qqa.UUID
                    join QUIZ_ATTEMPT qa
                      on qqa.FK_QUIZ_ATTEMPT_UUID= qa.UUID
                where 1=1
                  and qqaa.FK_QUIZ_ANSWER_UUID = ?
                  and qa.UUID = ?
              )
        """);

    query.setParameter(1, correct);
    query.setParameter(2, Instant.now());
    query.setParameter(3, quizAnswerUuid);
    query.setParameter(4, quizAnswerUuid);
    query.setParameter(5, quizAttemptUuid);

    query.executeUpdate();
  }

  public void answer(
    @NonNull String quizAttemptUuid,
    @NonNull String quizQuestionUuid,
    @NonNull String value) {
    Query query = em.createNativeQuery(
      """
              update QUIZ_QUESTION_ATTEMPT
              set
                FREE_TEXT = ?,
                ANSWERED_AT = ?
              where
                FK_QUIZ_ATTEMPT_UUID = ? and
                FK_QUIZ_QUESTION_UUID = ?
        """);

    query.setParameter(1, value);
    query.setParameter(2, Instant.now());
    query.setParameter(3, quizAttemptUuid);
    query.setParameter(4, quizQuestionUuid);
    query.executeUpdate();
  }
}
