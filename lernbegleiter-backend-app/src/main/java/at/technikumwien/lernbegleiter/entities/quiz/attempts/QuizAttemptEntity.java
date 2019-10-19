package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.*;

// connection between student and Quiz
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_ATTEMPT", indexes = {
  @Index(name = "I_QUIZ_ATTEMPT_FK_QUIZ_RUN_UUID", columnList = "FK_QUIZ_RUN_UUID"),
  @Index(name = "I_QUIZ_ATTEMPT_FK_STUDENT_UUID", columnList = "FK_STUDENT_UUID")
}, uniqueConstraints = {
  @UniqueConstraint(
    name = "UC_QUIZ_ATTEMPT_FK_QUIZ_RUN_UUID_FK_STUDENT_UUID",
    columnNames = {"FK_QUIZ_RUN_UUID", "FK_STUDENT_UUID"})
})
@Entity
@NamedEntityGraph(name = "QuizAttempt.allAnswers",
  attributeNodes = {
    @NamedAttributeNode("student"),
    @NamedAttributeNode(value = "quizQuestionAttempts", subgraph = "QuizQuestionAttempt.answers")},
  subgraphs = @NamedSubgraph(
    name = "QuizQuestionAttempt.answers",
    attributeNodes = {
      @NamedAttributeNode("answers")
    }
  )
)
public class QuizAttemptEntity extends BaseEntityCreationUpdateDate<QuizAttemptEntity> {
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_QUIZ_RUN_UUID", nullable = false)
  private QuizRunEntity quizRun;

  @Column(name = "FK_QUIZ_RUN_UUID", updatable = false, insertable = false)
  private String fkQuizRunUUID;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "quizAttempt")
  @Fetch(FetchMode.JOIN)
  private Set<QuizQuestionAttemptEntity> quizQuestionAttempts = new HashSet<>();

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  @Column(name = "FK_STUDENT_UUID", updatable = false, insertable = false)
  private String fkStudentUuid;
}
