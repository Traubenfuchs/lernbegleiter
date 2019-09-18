package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_ANSWER", indexes = {
        @Index(name = "I_QUIZ_ANSWER_FK_QUIZ_QUESTION_UUID", columnList = "FK_QUIZ_QUESTION_UUID")
})
@Entity
public class QuizAnswerEntity extends BaseEntityCreationUpdateDate<QuizAnswerEntity> {
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_QUESTION_UUID", nullable = false)
    private QuizQuestionEntity quizQuestion;
    @Column(name = "CORRECT", nullable = false)
    private Boolean correct;
    @Column(name = "POSITION", nullable = false)
    private Integer position;
}
