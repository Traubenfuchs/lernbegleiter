package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.data.QuizType;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

// can be connected to class - only students of that class can take the quiz
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ")
@Entity
public class QuizEntity extends BaseEntityCreationUpdateDate<QuizEntity> {
    @Column(name = "MAX_RETRIES", nullable = false)
    private Integer maxRetries;
    @Column(nullable = false)
    private String name;
    @Column(length = 1024)
    private String description;
    @OneToMany(mappedBy = "quiz")
    private Set<QuizQuestionEntity> questions;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_AUTHOR_UUID", nullable = false)
    private UserEntity author;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizType quizType;
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Instant expirationDate;
    @OneToMany(mappedBy = "quiz")
    private Set<QuizRunEntity> quizRuns = new HashSet<>();
}
