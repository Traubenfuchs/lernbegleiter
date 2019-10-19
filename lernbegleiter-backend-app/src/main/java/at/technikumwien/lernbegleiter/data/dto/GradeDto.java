package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto extends BaseDto<GradeDto> {
  private String name;
  private Set<StudentDto> students;
  private Set<ClassDto> classes;
}
