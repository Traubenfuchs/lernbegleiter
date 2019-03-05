package at.technikumwien.xxx.entities.auth;

import at.technikumwien.xxx.entities.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Accessors(chain = true)
@Data
@Table(name = "USER")
@Entity
public class UserEntity extends BaseEntity<UserEntity> {
  @Column(nullable = false, unique = true)
  private String email;
  private String firstName;
  private String familyName;
  private LocalDate birthday;
  @Column(nullable = false)
  @Lob
  private byte[] hashedAndSaltedPassword;
  @ElementCollection
  @CollectionTable(name="USER_RIGHTS", joinColumns=@JoinColumn(name="USER_UUID"))
  @Column(name="RIGHT")
  private Set<String> rights;
}
