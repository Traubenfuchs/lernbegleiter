package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultEntryDto {
  private String name;
  private Integer points = 0;
  private Integer weightedPoints = 0;

  public QuizResultEntryDto incrementPoints() {
    this.points++;
    return this;
  }

  public QuizResultEntryDto incrementWeightedPointsBy(int value) {
    this.weightedPoints += value;
    return this;
  }
}
