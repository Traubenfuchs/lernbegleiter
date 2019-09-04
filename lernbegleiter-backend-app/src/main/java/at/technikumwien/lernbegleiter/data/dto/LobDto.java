package at.technikumwien.lernbegleiter.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LobDto {
    private String quizPictureBase64;
    private String quizPictureUUID;
    private String quizPictureFileName;
}
