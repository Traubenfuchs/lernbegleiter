package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewClassDayDto extends BaseDto<WeeklyOverviewClassDayDto> {
  private String teacherComment = "";
  private String studentComment = "";
}
