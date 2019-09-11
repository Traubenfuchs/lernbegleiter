package at.technikumwien.lernbegleiter.entities.quiz;

import at.technikumwien.lernbegleiter.entities.EntityWithLob;
import at.technikumwien.lernbegleiter.entities.LobEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "QUIZ_QUESTION", indexes = {
        @Index(name = "I_QUIZ_QUESTION_FK_QUIZ_UUID", columnList = "FK_QUIZ_UUID")
}, uniqueConstraints = {
        @UniqueConstraint(name = "UC_QUIZ_QUESTION_POSITION", columnNames = {"FK_QUIZ_UUID", "POSITION"})
})
@Entity
public class QuizQuestionEntity extends BaseEntityCreationUpdateDate<QuizQuestionEntity> implements EntityWithLob<QuizQuestionEntity> {
    @Column(nullable = false, length = 1024 * 10)
    private String content;
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_QUIZ_UUID", nullable = false)
    private QuizEntity quiz;
    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL)
    @OrderColumn(name = "POSITION")
    private List<QuizAnswerEntity> answers = new ArrayList<>();
    @Column(name = "POSITION", nullable = false)
    private Integer position;
    @Column(name = "TIME_LIMIT")
    private Integer timeLimit = 40;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_LOB_UUID")
    private LobEntity lob;
    @Column(name = "FK_LOB_UUID", updatable = false, insertable = false)
    private String fkLobUUID;
}
