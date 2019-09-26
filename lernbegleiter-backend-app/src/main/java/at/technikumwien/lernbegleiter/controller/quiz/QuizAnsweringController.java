package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.services.QuizRunService;
import at.technikumwien.lernbegleiter.services.quiz.QuizAnsweringService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RequestMapping("api")
@RestController
public class QuizAnsweringController extends BaseController {
  @Autowired
  private QuizRunService quizRunService;

  @Autowired
  private QuizAnsweringService quizAnsweringService;

  @PostMapping("quiz-run/{quizRunUUID}/quiz-attempt/{quizAttemptUUID}:answer")
  public QuizRunDto answer(
      @PathVariable String quizRunUUID,
      @PathVariable String quizAttemptUUID,
      @RequestBody AnswerRequest answerRequest) throws ExecutionException {
    quizAnsweringService.answer(
        quizAttemptUUID,
        answerRequest.getQuizAnswerUuid(),
        answerRequest.getCorrect());
    return quizRunService.getCachedForStudent(quizRunUUID);
  }

  @Data
  public static class AnswerRequest {
    private String quizAnswerUuid;
    private Boolean correct;
  }
}