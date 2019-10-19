package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.time.*;

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
