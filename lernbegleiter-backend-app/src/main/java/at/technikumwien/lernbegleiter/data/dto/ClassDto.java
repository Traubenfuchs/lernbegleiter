package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassDto extends BaseDto<ClassDto> {
  private String name;
  private Set<LearningModuleDto> learningModules;
  private String gradeName;
}
