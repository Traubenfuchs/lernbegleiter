package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
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
@Table(name = "QUIZ_RUN")
@Entity
public class QuizRunEntity extends BaseEntityCreationUpdateDate<QuizRunEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
}
