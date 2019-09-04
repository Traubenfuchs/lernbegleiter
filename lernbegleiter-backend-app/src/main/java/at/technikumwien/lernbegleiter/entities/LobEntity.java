package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "LOBS",indexes = {
    @Index(name = "I_FK_OWNER_UUID", columnList = "FK_OWNER_UUID")
})
public class LobEntity extends BaseEntityCreationUpdateDate<LobEntity> {
  @Column(name = "MD5", nullable = false)
  private String md5;
  @Column(name = "BYTES", nullable = false)
  @Lob
  private byte[] bytes;
  @Column(name = "FILENAME")
  private String filename;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "FK_OWNER_UUID")
  private UserEntity owner;

  @Column(name="FK_OWNER_UUID", insertable = false, updatable = false)
  private String fkOwnerUuid;

  public LobEntity setBytes(byte[] bytes) throws NoSuchAlgorithmException {
    if (bytes == null) {
      md5 = null;
    } else {
      MessageDigest md = null;
      md = MessageDigest.getInstance("MD5");
      md5 = new String(md.digest(bytes), StandardCharsets.UTF_8);
    }
    this.bytes = bytes;

    return this;
  }
}
