package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Class in German = Fach
 */
@Accessors(chain = true)
@Data
@Table(name = "CLASS")
@Entity
public class ClassEntity extends BaseEntityCreationUpdateDate<ClassEntity> {
    private String name;
    @OneToMany
    @JoinColumn(name = "CLASS_UUID", nullable = false)
    private Set<LearningModuleEntity> modules = new HashSet<>();
}
