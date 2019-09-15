package at.technikumwien.lernbegleiter.data.dto.reflexion;

import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
