package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewClassDto extends BaseDto<WeeklyOverviewClassDto> {
  private String name = "";
  private String color = "";
  private List<WeeklyOverviewClassDayDto> days = new ArrayList<>();
}
