package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "SUB_MODULE",indexes = {
        @Index(name="I_SUB_MODULE_FK_LEARNING_MODULE_UUID", columnList = "FK_LEARNING_MODULE_UUID")
})
@Entity
public class SubModuleEntity extends BaseEntityCreationUpdateDate<SubModuleEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_LEARNING_MODULE_UUID", nullable = false)
  private LearningModuleEntity parent;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DATE_START", nullable = false)
  private LocalDate start;

  @Column(name="DATE_DEADLINE", nullable = false)
  private LocalDate deadline;

  @OneToMany(mappedBy = "subModule")
  private Set<SubModuleStudentEntity> subModuleStudents = new HashSet<>();

  @Column(name="DESCRIPTION")
  private String description;
}
