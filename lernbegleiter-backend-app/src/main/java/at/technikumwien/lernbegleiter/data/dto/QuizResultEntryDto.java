package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultEntryDto {
  private String name;
  private Integer points = 0;

  public QuizResultEntryDto incrementPoints() {
    this.points++;
    return this;
  }
}
