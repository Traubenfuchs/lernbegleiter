package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_RUN",indexes = {
        @Index(name="I_QUIZ_RUN_FK_QUIZ_UUID", columnList = "FK_QUIZ_UUID")
})
@Entity
public class QuizRunEntity extends BaseEntityCreationUpdateDate<QuizRunEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
}
