package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "SUB_MODULE_STUDENT")
@Entity
public class SubModuleStudentEntity extends BaseEntityCreationUpdateDate<SubModuleStudentEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_SUB_MODULE_UUID", nullable = false)
  private SubModuleEntity subModule;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_STUDENT_UUID", nullable = false)
  private UserEntity student;

  @Column(name="DT_FINISHED_AT")
  private Instant finishedAt;
}
