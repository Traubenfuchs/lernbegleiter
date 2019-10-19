package at.technikumwien.lernbegleiter.data.requests;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Data
@Accessors(chain = true)
public class UserUpdateDto {
  private String password;
  private String email;
  private LocalDate birthday;
  private String firstName;
  private String familyName;
}
