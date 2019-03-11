package at.technikumwien.lernbegleiter.data.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
  private String password;
  private String email;
  private LocalDate birthday;
  private String firstName;
  private String familyName;
}
