package at.technikumwien.lernbegleiter.data.dto.quiz.attempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionAnswerAttemptDto {
    private String answer;
    private Boolean correct;
}
