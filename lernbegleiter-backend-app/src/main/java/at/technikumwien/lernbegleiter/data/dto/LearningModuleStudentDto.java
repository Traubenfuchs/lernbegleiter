package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LearningModuleStudentDto extends BaseDto<LearningModuleStudentDto> {
  private LocalDate dueDate;
  private Instant finishedAt;
  private String name;
  private Set<SubModuleStudentDto> subModules;
}
