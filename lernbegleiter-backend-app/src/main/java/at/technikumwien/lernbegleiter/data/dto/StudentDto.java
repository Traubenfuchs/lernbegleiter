package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private String uuid;
    private String email;
    private String firstName;
    private String familyName;
    private LocalDate birthday;
    private String password;
}
