package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto extends BaseDto<ClassDto> {
    private String name;
    private Set<LearningModuleDto> learningModules;
    private String gradeName;
}
