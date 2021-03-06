package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Accessors(chain = true)
@Getter
@Setter
@Table(
  name = "LOGINS",
  indexes = {
    @Index(name = "I_LOGIN_ENTITY_FK_USER_UUID", columnList = "FK_USER_UUID")
  })
@Entity
public class LoginEntity extends BaseEntityCreationDate<LoginEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name = "FK_USER_UUID", nullable = false, updatable = false)
  private UserEntity user;

  @Column(name = "SECRET", nullable = false)
  private String secret;
}
