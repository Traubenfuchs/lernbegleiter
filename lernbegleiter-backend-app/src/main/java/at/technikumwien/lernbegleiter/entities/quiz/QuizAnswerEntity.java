package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
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
@Table(name = "QUIZ_ANSWER")
@Entity
public class QuizAnswerEntity extends BaseEntityCreationUpdateDate<QuizAnswerEntity> {
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_ANSWER_UUID", nullable = false)
    private QuizQuestionEntity quizQuestion;
}