package at.technikumwien.lernbegleiter.data.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;

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
}
