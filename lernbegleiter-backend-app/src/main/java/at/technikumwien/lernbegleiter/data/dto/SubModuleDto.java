package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubModuleDto extends BaseDto<SubModuleDto> {
  private String name;
  private LocalDate deadline;
  private LocalDate start;
  private String description;
}
