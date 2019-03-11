package at.technikumwien.lernbegleiter.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class StudentDto {
    private String uuid;
    private String email;
    private String firstName;
    private String familyName;
    private LocalDate birthday;
}
