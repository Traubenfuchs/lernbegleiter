package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
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
