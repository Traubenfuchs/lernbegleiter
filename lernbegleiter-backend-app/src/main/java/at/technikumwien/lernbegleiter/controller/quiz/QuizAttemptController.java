package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class QuizAttemptController {
  @Autowired
  private QuizAttemptService quizAttemptService;

  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt:create-if-not-exists")
  public UuidResponse createQuizAttemptIfNotExists(@PathVariable String quizRunUUID) {
    return new UuidResponse(quizAttemptService.createQuizAttemptIfNotExists(quizRunUUID));
  }
}
