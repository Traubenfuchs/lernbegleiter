package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_QUESTION_ANSWER_ATTEMPT", indexes = {
  @Index(name = "I_QUIZ_QUESTION_ANSWER_ATTEMPT_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID", columnList = "FK_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID"),
  @Index(name = "I_QUIZ_QUESTION_ANSWER_ATTEMPT_QUIZ_ANSWER_UUID", columnList = "FK_QUIZ_ANSWER_UUID")
})
@Entity
public class QuizQuestionAnswerAttemptEntity extends BaseEntityCreationUpdateDate<QuizQuestionAnswerAttemptEntity> {
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID", nullable = false)
  private QuizQuestionAttemptEntity quizQuestionAttempt;

  @Column(name = "FK_QUIZ_QUESTION_ANSWER_ATTEMPT_UUID", updatable = false, insertable = false)
  private String fkQuizQuestionAttemptUuid;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_QUIZ_ANSWER_UUID", nullable = false)
  private QuizAnswerEntity quizAnswer;

  @Column(name = "FK_QUIZ_ANSWER_UUID", updatable = false, insertable = false)
  private String fkQuizAnswerUuid;

  @Column(name = "CORRECT", nullable = false)
  private Boolean correct;
}

