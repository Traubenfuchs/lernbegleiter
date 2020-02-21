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
public class QuizDto extends BaseDto<QuizDto> {
  private Integer maxRetries = 0;
  private String name;
  private String description;
  private Set<QuizQuestionDto> questions = new HashSet<>();
  private TeacherDto author;
  private Set<QuizRunDto> quizRuns = new HashSet<>();

  public QuizDto addQuestions(QuizQuestionDto... quizQuestionDtos) {
    for (QuizQuestionDto quizQuestionDto : quizQuestionDtos) {
      questions.add(quizQuestionDto);
    }

    return this;
  }
}
