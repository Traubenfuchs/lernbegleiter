package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "USERS", indexes = {
    @Index(name = "I_USERS_FK_GRADE_UUID", columnList = "FK_GRADE_UUID")
})
@Entity
public class UserEntity extends BaseEntityCreationUpdateDate<UserEntity> {
  @Column(name = "EMAIL", nullable = false, unique = true)
  private String email;
  @Column(name = "FIRST_NAME")
  private String firstName;
  @Column(name = "FAMILY_NAME")
  private String familyName;
  @Column(name = "BIRTHDAY")
  private LocalDate birthday;

  @Column(name = "HASHED_AND_SALTED_PASSWORD", nullable = false)
  @Lob
  private byte[] hashedAndSaltedPassword;

  @ElementCollection(fetch = EAGER)
  @CollectionTable(name = "USER_RIGHTS", joinColumns = @JoinColumn(name = "FK_USER_UUID"))
  @Column(name = "RIGHT_NAME", nullable = false)
  private Set<String> rights = new HashSet<>();

  /**
   * Grades this teacher is head teacher of
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "classTeacher", fetch = LAZY)
  //@Fetch(value = FetchMode.JOIN)
  private Set<GradeEntity> grades = new HashSet<>();

  /**
   * Grade this student belongs to
   */
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "FK_GRADE_UUID")
  private GradeEntity grade;

  /**
   * weekly overviews
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = LAZY)
  //@Fetch(value = FetchMode.JOIN)
  private Set<WeeklyOverviewEntity> weeklyOverviews = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = LAZY)
  //@Fetch(value = FetchMode.JOIN)
  private Set<LearningModuleStudentEntity> learningModulesStudents = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = LAZY)
  // @Fetch(value = FetchMode.JOIN)
  private Set<SubModuleStudentEntity> subModuleStudents = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = LAZY)
  private Set<QuizEntity> quizes = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = LAZY)
  private Set<QuizAttemptEntity> quizAttempts = new HashSet<>();
}
