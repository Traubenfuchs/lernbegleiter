package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import com.google.zxing.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
public class QuizQrCodeController {
  @Autowired
  private QuizQrCodeService quizQrCodeService;

  @GetMapping("api/quiz/{quizRunUuid}/qr")
  public QuizQrCodeResponse get(@PathVariable String quizRunUuid) throws IOException, WriterException {
    return quizQrCodeService.get(quizRunUuid);
  }
}