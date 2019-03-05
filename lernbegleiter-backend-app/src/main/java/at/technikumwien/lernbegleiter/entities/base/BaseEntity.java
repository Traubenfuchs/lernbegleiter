package at.technikumwien.lernbegleiter.entities.base;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {
  @GenericGenerator(
      name = "UUID_GENERATOR",
      strategy = "at.technikumwien.lernbegleiter.entities.base.UuidGenerator")
  @GeneratedValue(generator = "UUID_GENERATOR")
  @Id
  private String uuid;

  public T generateUuid() {
    this.uuid = UUID.randomUUID().toString();
    return (T)this;
  }
}
