package at.technikumwien.lernbegleiter.data.responses;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Accessors(chain = true)
@Data
public class LoginResponse {
  private String uuid;
  private String secret;
  private Set<String> rights;
}
