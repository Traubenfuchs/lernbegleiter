package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "LEARNING_MODULE_STUDENT")
@Entity
public class LearningModuleStudentEntity extends BaseEntityCreationUpdateDate<LearningModuleStudentEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_LEARNING_MODULE_UUID", nullable = false)
  private LearningModuleEntity learningModule;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  @Column(name="DT_FINISHED_AT")
  private Instant finishedAt;
}
