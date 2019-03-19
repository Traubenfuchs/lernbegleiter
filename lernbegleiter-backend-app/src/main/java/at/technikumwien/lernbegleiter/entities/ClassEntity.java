package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassEntity;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewReflectionClassEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column(name = "NAME", nullable = false)
    private String name;
    @OneToMany(mappedBy = "parent")
    private Set<LearningModuleEntity> modules = new HashSet<>();
    @OneToMany(mappedBy = "clazz")
    private Set<WeeklyOverviewReflectionClassEntity> weeklyOverviewReflectionClasses = new HashSet<>();
    @OneToMany(mappedBy = "clazz")
    private Set<WeeklyOverviewClassEntity> weeklyOverviewClasses = new HashSet<>();
}
