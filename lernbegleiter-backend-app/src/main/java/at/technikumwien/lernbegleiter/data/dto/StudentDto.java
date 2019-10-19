package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends BaseDto<StudentDto> {
  private String email;
  private String firstName;
  private String familyName;
  private LocalDate birthday;
  private String password;
  private String gradeName;
}
