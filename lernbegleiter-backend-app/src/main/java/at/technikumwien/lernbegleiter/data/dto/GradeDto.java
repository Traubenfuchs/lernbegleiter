package at.technikumwien.lernbegleiter.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class GradeDto {
    private String uuid;
    private String name;
    private Set<StudentDto> students;
}
