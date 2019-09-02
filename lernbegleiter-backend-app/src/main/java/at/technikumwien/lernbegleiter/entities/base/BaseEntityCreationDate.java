package at.technikumwien.lernbegleiter.entities.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.Instant;

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
