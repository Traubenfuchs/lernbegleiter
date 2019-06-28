package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
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
@Table(name = "QUIZ_QUESTION")
@Entity
public class QuizQuestionEntity extends BaseEntityCreationUpdateDate<QuizQuestionEntity> {
    @Column(nullable = false, length = 1024*10)
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
    @OneToMany(mappedBy = "quizQuestion")
    private Set<QuizAnswerEntity> answers = new HashSet<>();
}
