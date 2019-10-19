package at.technikumwien.lernbegleiter.data.requests;

import lombok.*;

import javax.validation.constraints.*;

@Data
public class LoginRequest {
  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
}
