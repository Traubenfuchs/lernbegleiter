package at.technikumwien.lernbegleiter.data.dto.reflexion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewClassDayDto {
    private String uuid = "";
    private String teacherComment = "";
    private String studentComment = "";
}
