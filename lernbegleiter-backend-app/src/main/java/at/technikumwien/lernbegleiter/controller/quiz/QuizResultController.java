package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("api")
@RestController
public class QuizResultController extends BaseController {
  private final QuizResultService quizResultService;

  @GetMapping("quiz/{quizUUID}/quiz-run/{quizRunUUID}/quiz-result")
  public QuizResultDto get(@PathVariable String quizUUID, @PathVariable String quizRunUUID) {
    return quizResultService.getQuizResult(quizUUID, quizRunUUID);
  }
}
