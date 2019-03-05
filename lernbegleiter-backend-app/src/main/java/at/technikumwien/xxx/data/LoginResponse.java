package at.technikumwien.xxx.data;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Accessors(chain = true)
@Data
public class LoginResponse {
  private String uuid;
  private String secret;
  private Set<String> rights;
}
