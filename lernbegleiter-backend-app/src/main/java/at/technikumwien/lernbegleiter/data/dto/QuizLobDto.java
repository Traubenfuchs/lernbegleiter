package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizLobDto {
  private String quizPictureBase64;
  private String quizPictureUUID;
  private String quizPictureFileName;
}
