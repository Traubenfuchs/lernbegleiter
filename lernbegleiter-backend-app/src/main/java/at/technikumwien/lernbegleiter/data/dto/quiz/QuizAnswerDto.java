package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerDto extends BaseDto<QuizAnswerDto> {
  private String content;
  private Boolean correct;
  private Integer position;
}
