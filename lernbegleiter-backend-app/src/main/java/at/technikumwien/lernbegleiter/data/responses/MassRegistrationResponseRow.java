package at.technikumwien.lernbegleiter.data.responses;

import lombok.*;
import lombok.experimental.*;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class MassRegistrationResponseRow {
  private String urlForLogin;
  private String username;
  private String password;
}