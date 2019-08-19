package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LearningModuleDto extends BaseDto<LearningModuleDto> {
    private String name;
    private Set<SubModuleDto> learningModules;
    private LocalDate deadline;
    private LocalDate start;
    private String description;
}
