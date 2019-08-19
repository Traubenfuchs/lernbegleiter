package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_QUESTION", indexes = {
        @Index(name = "I_QUIZ_QUESTION_FK_QUIZ_UUID", columnList = "FK_QUIZ_UUID")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"FK_QUIZ_UUID", "ORDER"})
})
@Entity
public class QuizQuestionEntity extends BaseEntityCreationUpdateDate<QuizQuestionEntity> {
    @Column(nullable = false, length = 1024 * 10)
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
    @OneToMany(mappedBy = "quizQuestion")
    private Set<QuizAnswerEntity> answers = new HashSet<>();
    @Column(name = "ORDER", nullable = false)
    private Integer order;
}
