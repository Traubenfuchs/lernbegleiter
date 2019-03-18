package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "LEARNING_MODULE")
@Entity
public class LearningModuleEntity extends BaseEntityCreationUpdateDate<LearningModuleEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_CLASS_UUID", nullable = false)
    private ClassEntity parent;
    private String name;
    @OneToMany(mappedBy = "parent")
    private Set<SubModuleEntity> subModules = new HashSet<>();
}
