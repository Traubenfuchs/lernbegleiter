package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import lombok.*;
import lombok.experimental.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.*;

/**
 * Class in German = Fach
 */
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "CLASS", indexes = {
  @Index(name = "I_CLASS_FK_GRADE_UUID", columnList = "FK_GRADE_UUID")
})
@Entity
public class ClassEntity extends BaseEntityCreationUpdateDate<ClassEntity> {
  @Column(name = "NAME", nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "clazz", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<LearningModuleEntity> modules = new HashSet<>();

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "clazz", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewReflectionClassEntity> weeklyOverviewReflectionClasses = new HashSet<>();

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "clazz", fetch = EAGER, orphanRemoval = true)
  @Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();

  @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "classEntity", fetch = LAZY, orphanRemoval = true)
  @Fetch(value = FetchMode.SELECT)
  private Set<Class2LobEntity> lobs = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "FK_GRADE_UUID")
  private GradeEntity grade;

  @Column(name = "color")
  private String color;
}
