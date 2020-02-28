package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MassRegistrationRowDto extends BaseDto<MassRegistrationRowDto> {
  private String secret;
  private String username;
  private String password;
}