package at.technikumwien.lernbegleiter.data;

import lombok.*;

@Data
@AllArgsConstructor
public class QuizQrCodeResponse {
  private String qrCodeImageAsBase64;
}