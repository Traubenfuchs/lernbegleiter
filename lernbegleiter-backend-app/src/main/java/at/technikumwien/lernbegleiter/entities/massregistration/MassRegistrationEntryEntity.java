package at.technikumwien.lernbegleiter.entities.massregistration;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "MASS_REGISTRATION_ENTRY", indexes = {})
public class MassRegistrationEntryEntity extends BaseEntityCreationUpdateDate<MassRegistrationEntryEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_MASS_REGISTRATION_ENTRY", nullable = false)
  private MassRegistrationEntity owner;
  @Column(name = "USERNAME")
  private String username;
  @Column(name = "PASSWORD")
  private String password;
  @Column(name = "SECRET")
  private String secret;

  @OneToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "FK_USER_UUID", unique = true, nullable = false, updatable = false)
  private UserEntity createdUser;
}