package at.technikumwien.lernbegleiter.entities.quiz.attempts;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Instances of a quiz
 */
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_RUN", indexes = {
        @Index(name = "I_QUIZ_RUN_FK_QUIZ_UUID", columnList = "FK_QUIZ_UUID")
})
@Entity
public class QuizRunEntity extends BaseEntityCreationUpdateDate<QuizRunEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;

    @Column(name = "FK_QUIZ_UUID", updatable = false, insertable = false)
    private String fkQuizUuid;

    @Column(name = "NEXT_TIME_LIMIT")
    private Instant nextTimeLimit;

    @Column(name = "STARTED_AT")
    private Instant startedAt;

    @ManyToOne()
    @JoinColumn(name = "FK_QUIZ_QUESTION_UUID")
    private QuizQuestionEntity currentQuestion = null;

    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizRunState state;

    @OneToMany(mappedBy = "quizRun")
    private Set<QuizAttemptEntity> attempts = new HashSet<>();
}