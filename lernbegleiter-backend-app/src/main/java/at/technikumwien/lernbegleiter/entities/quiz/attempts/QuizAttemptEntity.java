package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// connection between student and Quiz
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_ATTEMPT")
@Entity
public class QuizAttemptEntity extends BaseEntityCreationUpdateDate<QuizAttemptEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
    @OneToMany(mappedBy = "quizAttempt")
    private Set<QuizQuestionAttemptEntity> quizQuestionAttempts = new HashSet<>();
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
    private UserEntity student;
}
