package at.technikumwien.lernbegleiter.data.dto.quiz.attempt;

import lombok.*;
import lombok.experimental.*;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionAnswerAttemptDto {
  private String answer;
  private Boolean correct;
}
