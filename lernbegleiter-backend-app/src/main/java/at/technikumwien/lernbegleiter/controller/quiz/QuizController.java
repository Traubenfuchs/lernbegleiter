package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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