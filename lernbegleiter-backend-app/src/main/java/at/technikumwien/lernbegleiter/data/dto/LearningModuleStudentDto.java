package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LearningModuleStudentDto extends BaseDto<LearningModuleStudentDto> {
  private LocalDate dueDate;
  private Instant finishedAt;
  private String name;
  private Boolean late;
  private String color;
  private String learningModuleUuid;
}
