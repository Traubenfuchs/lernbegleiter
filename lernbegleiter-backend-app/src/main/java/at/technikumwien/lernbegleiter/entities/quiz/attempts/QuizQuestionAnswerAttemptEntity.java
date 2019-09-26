package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizAnswerEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

