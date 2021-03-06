package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.util.*;

// can be connected to class - only students of that class can take the quiz
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ", indexes = {
  @Index(name = "I_QUIZ_FK_AUTHOR_UUID", columnList = "FK_AUTHOR_UUID")
})
@Entity
@NamedEntityGraphs({
  @NamedEntityGraph(name = "Quiz.allAnswers",
    attributeNodes = {
      @NamedAttributeNode(value = "questions", subgraph = "QuizQuestionEntity.allAnswers")},
    subgraphs = @NamedSubgraph(name = "QuizQuestionEntity.allAnswers",
      attributeNodes = @NamedAttributeNode("answers")))
})
public class QuizEntity extends BaseEntityCreationUpdateDate<QuizEntity> {
  @Column(name = "MAX_RETRIES", nullable = false)
  private Integer maxRetries;
  @Column(nullable = false)
  private String name;
  @Column(length = 1024)
  private String description;
  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
  @OrderBy("position")
  // @Fetch(FetchMode.JOIN)
  private List<QuizQuestionEntity> questions = new ArrayList<>();
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_AUTHOR_UUID", nullable = false)
  private UserEntity author;
  @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
  private Set<QuizRunEntity> quizRuns = new HashSet<>();
}
