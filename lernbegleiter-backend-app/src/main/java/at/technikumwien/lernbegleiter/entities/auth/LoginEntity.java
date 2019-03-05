package at.technikumwien.lernbegleiter.entities.auth;

import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationDate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@Table(name = "LOGINS")
@Entity
public class LoginEntity extends BaseEntityCreationDate<LoginEntity> {
  @ManyToOne(optional = false)
  @JoinColumn(name="USER_UUID", nullable=false, updatable=false)
  private UserEntity user;

  @Column(nullable = false)
  private String secret;
}
