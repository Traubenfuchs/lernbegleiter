package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * GradeDto = Unterrichtsstufe / Schulklasse
 */
@Accessors(chain = true)
@Data
@Table(name = "GRADE")
@Entity
@EqualsAndHashCode(exclude={"students", "classes"})
public class GradeEntity extends BaseEntityCreationUpdateDate<GradeEntity> {

  @Column(name = "NAME", nullable = false, unique = true)
  private String name;

  /**
   * Students that belong to this class
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
  private Set<UserEntity> students = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "FK_HEADTEACHER_UUID")
  private UserEntity classTeacher;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
  private Set<ClassEntity> classes = new HashSet<>();
}
