package at.technikumwien.lernbegleiter.data;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LobDto {
  private String uuid;
  private String filename;
  private String base64String;
  private Boolean visibleForModules = false;
}