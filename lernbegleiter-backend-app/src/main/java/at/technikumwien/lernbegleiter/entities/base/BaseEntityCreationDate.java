package at.technikumwien.lernbegleiter.entities.base;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.Instant;

@Data
@MappedSuperclass
public abstract class BaseEntityCreationDate<T extends BaseEntityCreationDate<T>> extends BaseEntity<T> {
  private Instant tsCreation;

  @PrePersist
  public void prePersist() {
    this.tsCreation = Instant.now();
  }
}
