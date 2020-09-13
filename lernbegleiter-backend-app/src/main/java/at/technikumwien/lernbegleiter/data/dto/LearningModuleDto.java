package at.technikumwien.lernbegleiter.data.dto;

import at.technikumwien.lernbegleiter.data.*;
import lombok.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LearningModuleDto extends BaseDto<LearningModuleDto> {
  private String name;
  private LocalDate deadline;
  private LocalDate start;
  private String description;
  private String color;
  private List<LobDto> lobs = new ArrayList<>();
}
