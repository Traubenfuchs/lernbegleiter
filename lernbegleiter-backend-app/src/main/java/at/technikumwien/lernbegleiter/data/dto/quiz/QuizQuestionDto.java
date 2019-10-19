package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionDto extends BaseDto<QuizQuestionDto> {
  private String content;
  private Set<QuizAnswerDto> answers = new HashSet<>();
  private Integer position;
  private LobDto lob;
  private Integer timeLimit;
}
