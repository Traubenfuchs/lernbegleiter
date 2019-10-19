package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "LEARNING_MODULE",
  uniqueConstraints = {
    @UniqueConstraint(name = "UC_LEARNING_MODULE_NAME_FK_CLASS_UUID", columnNames = {"NAME", "FK_CLASS_UUID"})},
  indexes = {
    @Index(name = "I_LEARNING_MODULE_FK_CLASS_UUID", columnList = "FK_CLASS_UUID")
  })
@Entity
public class LearningModuleEntity extends BaseEntityCreationUpdateDate<LearningModuleEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
  private ClassEntity clazz;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "DATE_START", nullable = false)
  private LocalDate start;

  @Column(name = "DATE_DEADLINE", nullable = false)
  private LocalDate deadline;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "learningModule")
  private Set<LearningModuleStudentEntity> learningModuleStudents = new HashSet<>();

  @Column(name = "DESCRIPTION")
  private String description;
}
