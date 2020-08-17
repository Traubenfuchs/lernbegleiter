package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.data.*;
import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.*;
import com.google.zxing.qrcode.decoder.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Service
public class QuizQrCodeService {
  private final static QRCodeWriter qrCodeWriter = new QRCodeWriter();
  private final static Base64.Encoder encoder = Base64.getEncoder();
  private final static Map<EncodeHintType, ErrorCorrectionLevel> qrSettings = Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

  private final static ConcurrentHashMap<String, QuizQrCodeResponse> qrCodeCache = new ConcurrentHashMap<>();

  @Value("${lernbegleiter.host}")
  private String host;

  public QuizQrCodeResponse get(String quizRunUuid) throws IOException, WriterException {
    return qrCodeCache.computeIfAbsent(quizRunUuid, key -> {
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        BitMatrix bitMatrix = qrCodeWriter.encode(
          host + "/management/quiz/current/quiz-run/" + quizRunUuid,
          BarcodeFormat.QR_CODE,
          250,
          250,
          qrSettings
        );

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return new QuizQrCodeResponse(encoder.encodeToString(baos.toByteArray()));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
  public void cleanQrCodeCache() {
    qrCodeCache.clear();
  }
}