package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto extends BaseDto<TeacherDto> {
  private String email;
  private String firstName;
  private String familyName;
  private LocalDate birthday;
  private String password;
}
