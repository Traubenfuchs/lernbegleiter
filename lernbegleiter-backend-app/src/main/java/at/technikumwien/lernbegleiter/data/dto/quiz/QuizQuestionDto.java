package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

import java.util.*;
import java.util.stream.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionDto extends BaseDto<QuizQuestionDto> {
  private String content;
  private Set<QuizAnswerDto> answers = new HashSet<>();
  private Integer position;
  private LobDto lob;
  private Integer timeLimit;
  private Boolean answeredCorrectly;
  private Integer answerCount;

  public QuizQuestionDto deepClone() {
    return new QuizQuestionDto()
      .setUuid(getUuid())
      .setTsCreation(getTsCreation())
      .setTsUpdate(getTsUpdate())
      .setContent(content)
      .setAnswers(answers.stream().map(QuizAnswerDto::deepClone).collect(Collectors.toSet()))
      .setPosition(position)
      .setLob(lob)
      .setTimeLimit(timeLimit)
      .setAnsweredCorrectly(answeredCorrectly)
      .setAnswerCount(answerCount)
      ;
  }
}
