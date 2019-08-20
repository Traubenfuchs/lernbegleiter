package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizAnswerEntity;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(
        name = "QUIZ_QUESTION_ATTEMPT",
        indexes = {
                @Index(name = "I_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_ATTEMPT_UUID", columnList = "FK_QUIZ_ATTEMPT_UUID"),
                @Index(name = "I_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_ANSWER_UUID", columnList = "FK_QUIZ_ANSWER_UUID"),
                @Index(name = "I_QUIZ_QUESTION_ATTEMPT_FK_QUIZ_QUESTION_UUID", columnList = "FK_QUIZ_QUESTION_UUID")
        }
)
@Entity
public class QuizQuestionAttemptEntity extends BaseEntityCreationUpdateDate<QuizQuestionAttemptEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_ATTEMPT_UUID", nullable = false)
    private QuizAttemptEntity quizAttempt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_ANSWER_UUID", nullable = false)
    private QuizAnswerEntity quizAnswer;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_QUESTION_UUID", nullable = false)
    private QuizQuestionEntity quizQuestion;
    private String extraContent;
}
