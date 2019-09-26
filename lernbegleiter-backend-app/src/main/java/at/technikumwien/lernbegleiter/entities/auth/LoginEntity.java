package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
