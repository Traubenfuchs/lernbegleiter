package at.technikumwien.lernbegleiter.data.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
public class RegistrationRequest {
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
  private LocalDate birthday;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String familyName;
}
