package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "SUB_MODULE")
@Entity
public class SubModuleEntity extends BaseEntityCreationUpdateDate<SubModuleEntity> {
    @ManyToOne(optional = false)
    @JoinColumn(name = "FK_LEARNING_MODULE_UUID", nullable = false)
    private LearningModuleEntity parent;
    private String name;
}
