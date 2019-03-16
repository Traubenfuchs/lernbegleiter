package at.technikumwien.lernbegleiter.entities.base;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

@Data
@MappedSuperclass
public abstract class BaseEntityCreationUpdateDate<T extends BaseEntityCreationUpdateDate<T>> extends  BaseEntity<T> {
  private Instant tsCreation;
  private Instant tsUpdate;

  @PrePersist
  public void prePersist() {
    Instant now = Instant.now();
    this.tsCreation = now;
    this.tsUpdate = now;
  }
  @PreUpdate
  public void preUpdate() {
    this.tsUpdate = Instant.now();
  }
}
