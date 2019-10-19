package at.technikumwien.lernbegleiter.data.dto.quiz.attempt;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionAttemptDto {
  private String question;
  private String questionImageUUID;
  private List<QuizQuestionAnswerAttemptDto> answers;
}
