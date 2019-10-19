package at.technikumwien.lernbegleiter.entities;

import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "LOBS", indexes = {
  @Index(name = "I_FK_OWNER_UUID", columnList = "FK_OWNER_UUID")
})
public class LobEntity extends BaseEntityCreationUpdateDate<LobEntity> {
  private final static Base64.Decoder decoder = Base64.getDecoder();

  public LobEntity(String bytesAsBase64String) {
    setBytes(decoder.decode(bytesAsBase64String));
  }

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
  @Column(name = "FK_OWNER_UUID", insertable = false, updatable = false)
  private String fkOwnerUuid;

  public LobEntity setBytes(byte[] bytes) {
    if (bytes == null) {
      md5 = null;
    } else {
      MessageDigest md = null;
      try {
        md = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("Can not happen.");
      }
      md5 = new String(md.digest(bytes), StandardCharsets.UTF_8);
    }
    this.bytes = bytes;

    return this;
  }
}
