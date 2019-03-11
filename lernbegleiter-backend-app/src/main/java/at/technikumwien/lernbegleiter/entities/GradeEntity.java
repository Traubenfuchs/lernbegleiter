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
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "GRADE_UUID")
    private Set<UserEntity> students = new HashSet<>();
}
