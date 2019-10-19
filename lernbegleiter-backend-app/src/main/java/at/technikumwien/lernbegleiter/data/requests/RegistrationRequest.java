package at.technikumwien.lernbegleiter.data.requests;

import lombok.*;
import lombok.experimental.*;

import javax.validation.constraints.*;
import java.time.*;

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
