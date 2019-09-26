package at.technikumwien.lernbegleiter.data.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UuidResponse {
  private String uuid;
}
