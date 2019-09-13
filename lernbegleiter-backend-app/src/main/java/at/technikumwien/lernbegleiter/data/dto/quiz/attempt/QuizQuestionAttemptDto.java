package at.technikumwien.lernbegleiter.data.dto.quiz.attempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionAttemptDto {
    private String question;
    private String questionImageUUID;
    private List<QuizQuestionAnswerAttempt> answers;
}
