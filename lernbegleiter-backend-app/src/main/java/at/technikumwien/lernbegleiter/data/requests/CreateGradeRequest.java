package at.technikumwien.lernbegleiter.data.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
public class CreateGradeRequest {
    @NotEmpty
    private String name;

}
