package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

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
