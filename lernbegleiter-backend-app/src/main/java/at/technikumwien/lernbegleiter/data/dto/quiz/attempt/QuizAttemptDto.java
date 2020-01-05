package at.technikumwien.lernbegleiter.data.dto.quiz.attempt;

import at.technikumwien.lernbegleiter.data.*;
import lombok.*;
import lombok.experimental.*;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptDto {
  private QuizQuestionAttemptState quizQuestionAttemptState = QuizQuestionAttemptState.ACTIVE;
}
