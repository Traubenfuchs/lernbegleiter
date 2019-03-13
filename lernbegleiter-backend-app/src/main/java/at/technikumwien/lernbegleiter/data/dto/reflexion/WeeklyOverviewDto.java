package at.technikumwien.lernbegleiter.data.dto.reflexion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewDto {
    private String uuid = "";
    private String kw = "";
    private String myWeeklyGoals = "";
    private String firstDayOfWeek = "";
    private String lastDayOfWeek = "";
    private String furtherSteps = "";
    private Set<WeeklyOverviewClassDto> weeklyOverviewClasses = new HashSet<>();
    private Set<WeeklyOverviewReflectionClassDto> reflexionClasses = new HashSet<>();
}
