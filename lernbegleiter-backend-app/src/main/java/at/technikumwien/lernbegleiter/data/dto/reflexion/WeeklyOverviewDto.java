package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewDto extends BaseDto<WeeklyOverviewDto> {
  private Integer calendarWeek;
  private Short year;
  private LocalDate firstDayOfWeek;
  private LocalDate lastDayOfWeek;
  private String myWeeklyGoals = "";
  private String furtherSteps = "";
  private List<WeeklyOverviewClassDto> weeklyOverviewClasses = new ArrayList<>();
  private List<WeeklyOverviewReflectionClassDto> reflexionClasses = new ArrayList<>();
}
