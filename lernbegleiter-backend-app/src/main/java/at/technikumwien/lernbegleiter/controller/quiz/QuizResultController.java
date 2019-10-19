package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class QuizResultController {
  @Autowired
  private QuizResultService quizResultService;

  @GetMapping("quiz/{quizUUID}/quiz-run/{quizRunUUID}/quiz-result")
  public QuizResultDto get(@PathVariable String quizUUID, @PathVariable String quizRunUUID) {
    return quizResultService.getQuizResult(quizUUID, quizRunUUID);
  }
}
