package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.dto.quiz.attempt.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("api")
@RestController
public class QuizAttemptController {
  private final QuizAttemptService quizAttemptService;


  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt:finish")
  public void finishAttempt(@PathVariable String quizRunUUID) {
    quizAttemptService.finishQuizAttempt(quizRunUUID);
  }

  @GetMapping("quiz-run/{quizRunUUID}/quiz-attempt")
  public QuizAttemptDto get(@PathVariable String quizRunUUID) {
    QuizAttemptDto quizQuestionAttemptDto = quizAttemptService.getByQuizRunUuidAndCurrentUser(quizRunUUID);
    return quizQuestionAttemptDto;
  }

  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt:create-if-not-exists")
  public UuidResponse createQuizAttemptIfNotExists(@PathVariable String quizRunUUID) {
    return new UuidResponse(quizAttemptService.createQuizAttemptIfNotExists(quizRunUUID));
  }
}
