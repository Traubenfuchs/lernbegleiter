package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.*;

/**
 * GradeDto = Unterrichtsstufe / Schulklasse
 */
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "GRADE", indexes = {
  @Index(name = "I_GRADE_FK_HEADTEACHER_UUID", columnList = "FK_HEADTEACHER_UUID")
})
@Entity
public class GradeEntity extends BaseEntityCreationUpdateDate<GradeEntity> {

  @Column(name = "NAME", nullable = false, unique = true)
  private String name;

  /**
   * Students that belong to this class
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade", fetch = LAZY)
  // @Fetch(value = FetchMode.JOIN)
  private Set<UserEntity> students = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "FK_HEADTEACHER_UUID")
  private UserEntity classTeacher;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade", fetch = LAZY)
  // @Fetch(value = FetchMode.JOIN)
  private Set<ClassEntity> classes = new HashSet<>();
}
