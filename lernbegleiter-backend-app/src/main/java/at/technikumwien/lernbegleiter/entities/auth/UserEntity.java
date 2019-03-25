package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.GradeEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleStudentEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "USERS")
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

    @ElementCollection
    @CollectionTable(name = "USER_RIGHTS", joinColumns = @JoinColumn(name = "FK_USER_UUID"))
    @Column(name = "RIGHT_NAME", nullable = false)
    private Set<String> rights = new HashSet<>();

    /**
     * Grades this teacher is head teacher of
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classTeacher")
    private Set<GradeEntity> grades = new HashSet<>();

    /**
     * Grade this student belongs to
     */
    @ManyToOne
    @JoinColumn(name = "FK_GRADE_UUID")
    private GradeEntity grade;

    /**
     * weekly overviews
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<WeeklyOverviewEntity> weeklyOverviews = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<LearningModuleStudentEntity> learningModulesStudents = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private Set<SubModuleStudentEntity> subModuleStudents = new HashSet<>();
}
