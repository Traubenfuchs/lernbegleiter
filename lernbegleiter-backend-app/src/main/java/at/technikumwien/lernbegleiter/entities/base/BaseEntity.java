package at.technikumwien.lernbegleiter.entities.base;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {
  @Column(name = "UUID", length = 36, nullable = false)
  @GenericGenerator(
      name = "UUID_GENERATOR",
      strategy = "at.technikumwien.lernbegleiter.entities.base.UuidGenerator")
  @GeneratedValue(generator = "UUID_GENERATOR")
  @Id
  private String uuid;

  public T setUuid(String uuid) {
    this.uuid = uuid;
    return (T) this;
  }

  public T generateUuid() {
    this.uuid = UUID.randomUUID().toString();
    return (T) this;
  }

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    if(uuid == null) {
      return 0;
    }
    return uuid.hashCode();
  }
}
