package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.QuizType;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// can be connected to class - only students of that class can take the quiz
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ", indexes = {
    @Index(name = "I_QUIZ_FK_AUTHOR_UUID", columnList = "FK_AUTHOR_UUID")
})
@Entity
@NamedEntityGraphs( {
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
  @OrderColumn(name = "POSITION")
  private List<QuizQuestionEntity> questions = new ArrayList<>();
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_AUTHOR_UUID", nullable = false)
  private UserEntity author;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private QuizType quizType;
  @OneToMany(mappedBy = "quiz")
  private Set<QuizRunEntity> quizRuns = new HashSet<>();
}
