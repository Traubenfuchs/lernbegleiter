package at.technikumwien.lernbegleiter.data.dto.reflexion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewDto {
    private String uuid = "";
    private Integer calendarWeek;
    private Short year;
    private LocalDate firstDayOfWeek;
    private LocalDate lastDayOfWeek;
    private String myWeeklyGoals = "";
    private String furtherSteps = "";
    private List<WeeklyOverviewClassDto> weeklyOverviewClasses = new ArrayList<>();
    private List<WeeklyOverviewReflectionClassDto> reflexionClasses = new ArrayList<>();
}
