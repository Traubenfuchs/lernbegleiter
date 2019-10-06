package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.quiz.QuizManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("api")
@RestController
public class QuizController extends BaseController {
  @Autowired
  private QuizManagementService quizManagementService;

  @GetMapping("quizzes")
  public Set<QuizDto> get() {
    authHelper.isAdminOrTeacherOrThrow();
    return quizManagementService.getAllQuizzes();
  }

  @GetMapping("quiz/{quizUuid}")
  public QuizDto get(@PathVariable String quizUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return quizManagementService.get(quizUuid);
  }

  @PutMapping("quiz/{quizUuid}")
  public void put(
      @RequestBody QuizDto quizDto,
      @PathVariable String quizUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    quizDto.setUuid(quizUuid);
    quizManagementService.put(quizDto);
  }

  @PostMapping("quiz")
  public UuidResponse post(@RequestBody QuizDto quizDto) {
    authHelper.isAdminOrTeacherOrThrow();
    quizDto.setUuid(null);
    return quizManagementService.post(quizDto);
  }

  @DeleteMapping("quiz/{quizUuid}")
  public void delete(@PathVariable String quizUuid) {
    quizManagementService.delete(quizUuid);
  }
}