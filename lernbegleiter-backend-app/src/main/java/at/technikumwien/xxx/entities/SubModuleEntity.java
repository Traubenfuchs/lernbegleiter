package at.technikumwien.xxx.entities;

import at.technikumwien.xxx.entities.base.BaseEntityCreationUpdateDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Table(name = "SUB_MODULE")
@Entity
public class SubModuleEntity extends BaseEntityCreationUpdateDate<SubModuleEntity> {
}
