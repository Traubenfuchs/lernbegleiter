package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

/**
 * GradeDto = Unterrichtsstufe / Schulklasse
 */
@Accessors(chain = true)
@Getter
@Setter
@Table(name = "GRADE",indexes = {
        @Index(name="I_GRADE_FK_HEADTEACHER_UUID", columnList = "FK_HEADTEACHER_UUID")
})
@Entity
public class GradeEntity extends BaseEntityCreationUpdateDate<GradeEntity> {

  @Column(name = "NAME", nullable = false, unique = true)
  private String name;

  /**
   * Students that belong to this class
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade", fetch = EAGER)
  @Fetch(value= FetchMode.JOIN)
  private Set<UserEntity> students = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "FK_HEADTEACHER_UUID")
  private UserEntity classTeacher;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade", fetch = EAGER)
  @Fetch(value= FetchMode.JOIN)
  private Set<ClassEntity> classes = new HashSet<>();
}
