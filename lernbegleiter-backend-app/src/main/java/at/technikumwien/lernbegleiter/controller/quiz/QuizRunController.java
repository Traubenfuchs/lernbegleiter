package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.*;

@AllArgsConstructor
@RequestMapping("api")
@RestController
public class QuizRunController {
  private final QuizRunService quizRunService;
  private final AuthHelper authHelper;

  @PostMapping("quiz/{quizUUID}/quiz-run")
  public UuidResponse post(
    @PathVariable String quizUUID,
    @RequestBody QuizRunDto quizRunDto) {
    authHelper.isAdminOrTeacherOrThrow();
    return quizRunService.post(quizUUID, quizRunDto);
  }

  @PutMapping("quiz-run/{quizRunUUID}")
  public void put(
    @PathVariable String quizRunUUID,
    @RequestBody QuizRunDto quizRunDto) {
    authHelper.isAdminOrTeacherOrThrow();
    quizRunDto.setUuid(quizRunUUID);
    quizRunService.put(quizRunDto);
  }

  @GetMapping("quiz/{quizUUID}/quiz-runs")
  public Collection<QuizRunDto> getRuns(@PathVariable String quizUUID) {
    authHelper.isAdminOrTeacherOrThrow();
    return quizRunService.getRuns(quizUUID);
  }

  @GetMapping({"quiz-run-admin/{quizRunUUID}"})
  public QuizRunDto getRunAdmin(@PathVariable String quizRunUUID) throws ExecutionException {
    authHelper.isAdminOrTeacherOrThrow();
    return quizRunService.getCachedForAdmin(quizRunUUID);
  }

  @GetMapping({"quiz-run-student/{quizRunUUID}"})
  public QuizRunDto getRunStudent(@PathVariable String quizRunUUID) throws ExecutionException {
    return quizRunService.getCachedForStudent(quizRunUUID);
  }

  @PostMapping("quiz-run/{quizRunUUID}:advance")
  public QuizRunDto advance(@PathVariable String quizRunUUID) throws ExecutionException {
    authHelper.isAdminOrTeacherOrThrow();
    quizRunService.advance(quizRunUUID);
    return getRunAdmin(quizRunUUID);
  }
}
