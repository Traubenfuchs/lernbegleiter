package at.technikumwien.lernbegleiter.data.responses;

import lombok.*;
import lombok.experimental.*;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UuidResponse {
  private String uuid;
}
