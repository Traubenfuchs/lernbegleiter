package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
