package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizAnswerEntity;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_QUESTION_ATTEMPT")
@Entity
public class QuizQuestionAttemptEntity extends BaseEntityCreationUpdateDate<QuizQuestionAttemptEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_ATTEMPT_UUID", nullable = false)
    private QuizAttemptEntity quizAttempt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_ANSWER_UUID", nullable = false)
    private QuizAnswerEntity quizAnswer;
    private String extraContent;
}
