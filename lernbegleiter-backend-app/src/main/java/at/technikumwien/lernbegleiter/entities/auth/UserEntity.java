package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizAttemptEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
    @OneToMany(mappedBy = "classTeacher", fetch = LAZY)
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
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "student", fetch = LAZY)
    private Set<WeeklyOverviewEntity> weeklyOverviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "student", fetch = LAZY)
    private Set<LearningModuleStudentEntity> learningModulesStudents = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user", fetch = LAZY)
    private Set<LoginEntity> logins = new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = LAZY)
    private Set<QuizEntity> quizes = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "student", fetch = LAZY)
    private Set<QuizAttemptEntity> quizAttempts = new HashSet<>();
}
