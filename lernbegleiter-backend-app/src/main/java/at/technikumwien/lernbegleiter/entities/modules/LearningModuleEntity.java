package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.*;
import java.util.*;

import static javax.persistence.FetchType.*;

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

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "learningModule", orphanRemoval = true)
  private Set<LearningModuleStudentEntity> learningModuleStudents = new HashSet<>();

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "learningModule", fetch = LAZY, orphanRemoval = true)
  @Fetch(value = FetchMode.SELECT)
  private Set<LearningModule2LobEntity> lobs = new HashSet<>();

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "COLOR")
  private String color;
}
