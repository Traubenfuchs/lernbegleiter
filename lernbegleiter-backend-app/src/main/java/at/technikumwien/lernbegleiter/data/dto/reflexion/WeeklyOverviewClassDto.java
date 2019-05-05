package at.technikumwien.lernbegleiter.data.dto.reflexion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyOverviewClassDto {
    private String uuid = "";
    private String name = "";
    private String color = "";
    private List<WeeklyOverviewClassDayDto> days = new ArrayList<>();
}
