package at.technikumwien.lernbegleiter.data.requests;

import lombok.*;
import lombok.experimental.*;

@Data
@Accessors(chain = true)
public class MassRegistrationRequest {
  private Integer amount = 1;
}
