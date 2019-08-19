package at.technikumwien.lernbegleiter.data.dto;

import lombok.Getter;

@Getter
public class BaseDto<T extends BaseDto<T>> {
    private String uuid;

    public T setUuid(String uuid) {
        this.uuid = uuid;
        return (T) this;
    }
}
