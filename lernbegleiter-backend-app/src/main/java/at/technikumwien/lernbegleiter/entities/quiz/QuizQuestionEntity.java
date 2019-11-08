package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.util.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_QUESTION", indexes = {
  @Index(name = "I_QUIZ_QUESTION_FK_QUIZ_UUID", columnList = "FK_QUIZ_UUID")
}, uniqueConstraints = {
  @UniqueConstraint(name = "UC_QUIZ_QUESTION_POSITION", columnNames = {"FK_QUIZ_UUID", "POSITION"})
})
@Entity
public class QuizQuestionEntity extends BaseEntityCreationUpdateDate<QuizQuestionEntity> implements EntityWithLob<QuizQuestionEntity> {
  @Column(nullable = false, length = 1024 * 10)
  private String content;
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
  private QuizEntity quiz;
  @Column(name = "FK_QUIZ_UUID", insertable = false, updatable = false)
  private String fkQuizzUuid;
  @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL)
  @OrderColumn(name = "POSITION")
  private List<QuizAnswerEntity> answers = new ArrayList<>();
  @Column(name = "POSITION", nullable = false)
  private Integer position;
  @Column(name = "TIME_LIMIT")
  private Integer timeLimit = 40;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_LOB_UUID")
  private LobEntity lob;
  @Column(name = "FK_LOB_UUID", updatable = false, insertable = false)
  private String fkLobUUID;
  @Column(name = "QUESTION_COUNT")
  private Integer answerCount;

  @PrePersist
  public void perPersistAnswersSize() {
    this.answerCount = getAnswers().size();
  }
}
