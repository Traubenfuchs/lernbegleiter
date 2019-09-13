package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizRunDto extends BaseDto<QuizRunDto> {
    private Instant nextTimeLimit;
    private QuizQuestionDto currentQuestion;
    private QuizRunState state;
}
