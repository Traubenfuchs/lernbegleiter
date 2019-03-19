package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "SUB_MODULE_STUDENT")
@Entity
public class SubModuleStudentEntity extends BaseEntityCreationUpdateDate<SubModuleStudentEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_SUB_MODULE_UUID", nullable = false)
  private SubModuleEntity subModule;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_LEARNING_MODULE_STUDENT_UUID", nullable = false)
  private LearningModuleStudentEntity learningModuleStudent;
}
