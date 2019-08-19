package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
//@AllArgsConstructor
@NoArgsConstructor
public class QuizRunDto extends BaseDto<QuizRunDto> {
}
