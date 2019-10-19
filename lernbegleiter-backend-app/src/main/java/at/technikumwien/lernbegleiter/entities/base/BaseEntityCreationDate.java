package at.technikumwien.lernbegleiter.entities.base;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntityCreationDate<T extends BaseEntityCreationDate<T>> extends BaseEntity<T> {
  @Column(name = "TS_CREATION", nullable = false, updatable = false)
  private Instant tsCreation;

  @PrePersist
  public void prePersist() {
    this.tsCreation = Instant.now();
  }
}
