package at.technikumwien.lernbegleiter.entities.modules;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "LEARNING_MODULE_2_LOB")
@Entity
public class LearningModule2LobEntity extends BaseEntity<LearningModule2LobEntity> {
  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "FK_LOB_UUID")
  private LobEntity lob;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_LEARNING_MODULE_UUID")
  private LearningModuleEntity learningModule;
}
