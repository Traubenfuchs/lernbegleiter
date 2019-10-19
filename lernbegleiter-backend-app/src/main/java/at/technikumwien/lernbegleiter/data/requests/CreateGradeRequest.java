package at.technikumwien.lernbegleiter.data.requests;

import lombok.*;
import lombok.experimental.*;

import javax.validation.constraints.*;

@Data
@Accessors(chain = true)
public class CreateGradeRequest {
  @NotEmpty
  private String name;

}
