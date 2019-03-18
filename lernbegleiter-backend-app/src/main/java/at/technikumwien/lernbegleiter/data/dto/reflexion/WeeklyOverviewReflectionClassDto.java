package at.technikumwien.lernbegleiter.data.dto.reflexion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewReflectionClassDto {
    private String uuid = "";
    private String color = "";
    private String progress = "";
    private String improvements = "";
    private String shortName = "";
    private String name = "";
}
