package at.technikumwien.lernbegleiter.data.dto;

import at.technikumwien.lernbegleiter.data.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MyQuizzesEntryDto {
  private String name;
  private QuizRunState runState;
  private String quizRunUuid;
  private String quizUuid;
  private QuizRunType quizType;
}
