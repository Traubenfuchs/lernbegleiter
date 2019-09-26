package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.QuizType;
import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import at.technikumwien.lernbegleiter.data.dto.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto extends BaseDto<QuizDto> {
  private Integer maxRetries = 0;
  private String name;
  private String description;
  private Set<QuizQuestionDto> questions = new HashSet<>();
  private TeacherDto author;
  private QuizType quizType = QuizType.ONE_QUESTION_AT_A_TIME;
  private Set<QuizRunDto> quizRuns = new HashSet<>();
}
