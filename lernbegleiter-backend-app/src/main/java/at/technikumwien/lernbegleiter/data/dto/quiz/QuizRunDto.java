package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizRunDto extends BaseDto<QuizRunDto> {
  private Instant nextTimeLimit;
  private Set<QuizQuestionDto> currentQuestions;
  private QuizRunState state;
  private QuizRunType quizRunType;
}
