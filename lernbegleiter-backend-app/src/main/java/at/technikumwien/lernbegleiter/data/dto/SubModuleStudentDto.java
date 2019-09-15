package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubModuleStudentDto extends BaseDto<SubModuleStudentDto> {
    private LocalDate dueDate;
    private Instant finishedAt;
    private String name;
}
