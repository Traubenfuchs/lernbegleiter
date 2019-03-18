package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * GradeDto = Unterrichtsstufe / Schulklasse
 */
@Accessors(chain = true)
@Data
@Table(name = "GRADE")
@Entity
public class GradeEntity extends BaseEntityCreationUpdateDate<GradeEntity> {

    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Students that belong to this class
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
    private Set<UserEntity> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "FK_HEADTEACHER_UUID")
    private UserEntity classTeacher;
}
