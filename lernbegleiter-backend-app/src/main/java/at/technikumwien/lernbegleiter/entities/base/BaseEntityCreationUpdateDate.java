package at.technikumwien.lernbegleiter.entities.base;

import lombok.*;

import javax.persistence.*;
import java.time.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntityCreationUpdateDate<T extends BaseEntityCreationUpdateDate<T>> extends BaseEntity<T> {
  @Column(name = "TS_CREATION", nullable = false, updatable = false)
  private Instant tsCreation;
  @Column(name = "TS_UPDATE", nullable = false, updatable = false)
  private Instant tsUpdate;

  @PrePersist
  public final void prePersistTsCreationAndTsUpdate() {
    Instant now = Instant.now();
    this.tsCreation = now;
    this.tsUpdate = now;
  }

  @PreUpdate
  public final void preUpdateTsUpdate() {
    this.tsUpdate = Instant.now();
  }
}
