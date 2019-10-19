package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewReflectionClassDto extends BaseDto<WeeklyOverviewReflectionClassDto> {
  private String color = "";
  private String progress = "";
  private String improvements = "";
  private String shortName = "";
  private String name = "";
}
