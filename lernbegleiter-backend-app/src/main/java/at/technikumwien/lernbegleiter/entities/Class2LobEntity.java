package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "CLASS_2_LOB")
@Entity
public class Class2LobEntity extends BaseEntity<Class2LobEntity> {
  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "FK_LOB_UUID")
  private LobEntity lob;

  @Column(name = "FK_LOB_UUID", insertable = false, updatable = false)
  private String fkLobUuid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_CLASS_UUID")
  private ClassEntity classEntity;

  @Column(name = "FK_CLASS_UUID", insertable = false, updatable = false)
  private String fkClassUuid;

  @Column(nullable = false, name = "VISIBLE_FOR_MODULES")
  private Boolean visibleForModules;
}
