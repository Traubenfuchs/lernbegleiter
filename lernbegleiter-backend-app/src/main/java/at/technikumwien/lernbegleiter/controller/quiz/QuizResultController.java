package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.dto.QuizResultDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
