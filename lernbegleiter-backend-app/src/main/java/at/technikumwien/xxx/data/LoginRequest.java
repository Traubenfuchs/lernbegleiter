package at.technikumwien.xxx.data;

import lombok.Data;

@Data
public class LoginRequest {
  private String email;
  private String password;
}
