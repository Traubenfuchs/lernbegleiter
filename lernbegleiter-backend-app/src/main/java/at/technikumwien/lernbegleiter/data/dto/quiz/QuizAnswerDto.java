package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizAnswerDto extends BaseDto<QuizAnswerDto> {
  private String content;
  private Boolean correct;
  private Boolean tickedCorrectly;
  private Integer position;

  public QuizAnswerDto deepClone() {
    return new QuizAnswerDto()
      .setUuid(getUuid())
      .setTsCreation(getTsCreation())
      .setTsUpdate(getTsUpdate())
      .setContent(content)
      .setCorrect(correct)
      .setTickedCorrectly(tickedCorrectly)
      .setPosition(position)
      ;
  }

}
