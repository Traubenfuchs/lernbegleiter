package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubModuleStudentDto {
    private String uuid;
    private LocalDate dueDate;
    private Instant finishedAt;
    private String name;
}
