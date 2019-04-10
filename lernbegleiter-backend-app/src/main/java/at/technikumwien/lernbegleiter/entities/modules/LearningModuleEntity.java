package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "LEARNING_MODULE")
@Entity
public class LearningModuleEntity extends BaseEntityCreationUpdateDate<LearningModuleEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
  private ClassEntity clazz;

  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(mappedBy = "parent")
  private Set<SubModuleEntity> subModules = new HashSet<>();

  @Column(name = "DATE_DEADLINE")
  private LocalDate deadline;

  @OneToMany(mappedBy = "learningModule")
  private Set<LearningModuleStudentEntity> learningModuleStudents = new HashSet<>();

  @Column(name="DESCRIPTION")
  private String description;
}
