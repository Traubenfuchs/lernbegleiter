package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

@RequestMapping("api")
@RestController
public class QuizAnsweringController extends BaseController {
  @Autowired
  private QuizRunService quizRunService;

  @Autowired
  private QuizAnsweringService quizAnsweringService;

  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt/{quizAttemptUUID}:tick")
  public QuizRunDto tick(
    @PathVariable String quizRunUUID,
    @PathVariable String quizAttemptUUID,
    @RequestBody TickRequest tickRequest) throws ExecutionException {
    quizAnsweringService.tick(
      quizAttemptUUID,
      tickRequest.getQuizAnswerUuid(),
      tickRequest.getCorrect());
    return quizRunService.getCachedForStudent(quizRunUUID);
  }

  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt/{quizAttemptUUID}:answer")
  public QuizRunDto answer(
    @PathVariable String quizRunUUID,
    @PathVariable String quizAttemptUUID,
    @RequestBody AnswerRequest tickRequest) throws ExecutionException {
    quizAnsweringService.answer(
      quizAttemptUUID,
      tickRequest.getValue());
    return quizRunService.getCachedForStudent(quizRunUUID);
  }

  @Data
  public static class TickRequest {
    private String quizAnswerUuid;
    private Boolean correct;
  }

  @Data
  public static class AnswerRequest {
    private String value;
  }
}