package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.*;
import java.util.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(
  name = "QUIZ_QUESTION_ATTEMPT",
  indexes = {
    @Index(name = "I_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_ATTEMPT_UUID", columnList = "FK_QUIZ_ATTEMPT_UUID"),
    @Index(name = "I_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_QUESTION_UUID", columnList = "FK_QUIZ_QUESTION_UUID")
  }, uniqueConstraints = {
  @UniqueConstraint(
    name = "UC_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_ATTEMPT_UUID_FK_QUIZ_QUESTION_UUID",
    columnNames = {"FK_QUIZ_ATTEMPT_UUID", "FK_QUIZ_QUESTION_UUID"})
})
@Entity
public class QuizQuestionAttemptEntity extends BaseEntityCreationUpdateDate<QuizQuestionAttemptEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_QUIZ_ATTEMPT_UUID", nullable = false)
  private QuizAttemptEntity quizAttempt;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "quizQuestionAttempt", fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  private Set<QuizQuestionAnswerAttemptEntity> answers = new HashSet<>();

  @Column(name = "FK_QUIZ_ATTEMPT_UUID", updatable = false, insertable = false)
  private String fkQuizAttemptUuid;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_QUIZ_QUESTION_UUID", nullable = false)
  private QuizQuestionEntity quizQuestion;

  @Column(name = "FK_QUIZ_QUESTION_UUID", updatable = false, insertable = false)
  private String fkQuizQuestionUuid;

  @Column(name = "EXTRA_CONTENT")
  private String extraContent;

  @Column(name = "FREE_TEXT")
  private String freeText;

  @Column(name = "ANSWERED_AT")
  private Instant answeredAt = Instant.now();
}
