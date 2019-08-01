package at.technikumwien.lernbegleiter.data.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class UserUpdateDto {
    private String password;
    private String email;
    private LocalDate birthday;
    private String firstName;
    private String familyName;
}
