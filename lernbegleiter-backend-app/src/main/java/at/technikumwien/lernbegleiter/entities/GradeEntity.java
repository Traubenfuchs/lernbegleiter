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
    /**
     * Students that belong to this class
     */
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grade")
    private Set<UserEntity> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "HEADTEACHER_UUID")
    private UserEntity classTeacher;
}
