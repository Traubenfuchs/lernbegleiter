package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Accessors(chain = true)
@Setter
@Getter
public class BaseDto<T extends BaseDto<T>> {
  private String uuid;
  private Instant tsCreation;
  private Instant tsUpdate;

  public T setUuid(String uuid) {
    this.uuid = uuid;
    return (T) this;
  }

  public T setTsCreation(Instant tsCreation) {
    this.tsCreation = tsCreation;
    return (T) this;
  }

  public T setTsUpdate(Instant tsUpdate) {
    this.tsUpdate = tsUpdate;
    return (T) this;
  }

  @Override
  public final boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public final int hashCode() {
    if (uuid == null) {
      return 0;
    }
    return uuid.hashCode();
  }
}
