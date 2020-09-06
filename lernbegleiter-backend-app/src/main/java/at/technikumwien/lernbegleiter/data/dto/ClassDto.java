package at.technikumwien.lernbegleiter.data.dto;

import at.technikumwien.lernbegleiter.data.*;
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
  private String color;
  private List<LobDto> lobs = new ArrayList<>();
  private String description;
}
