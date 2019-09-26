package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "LEARNING_MODULE_STUDENT", indexes = {
    @Index(name = "I_LEARNING_MODULE_STUDENT_FK_LEARNING_MODULE_UUID", columnList = "FK_LEARNING_MODULE_UUID"),
    @Index(name = "I_LEARNING_MODULE_STUDENT_FK_STUDENT_UUID", columnList = "FK_STUDENT_UUID")
})
@Entity
public class LearningModuleStudentEntity extends BaseEntityCreationUpdateDate<LearningModuleStudentEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_LEARNING_MODULE_UUID", nullable = false)
  private LearningModuleEntity learningModule;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  @Column(name = "DT_FINISHED_AT")
  private Instant finishedAt;
}
