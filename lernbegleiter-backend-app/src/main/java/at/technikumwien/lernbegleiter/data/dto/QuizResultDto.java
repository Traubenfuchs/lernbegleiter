package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
  private List<QuizResultEntryDto> entries = new ArrayList<>();
}