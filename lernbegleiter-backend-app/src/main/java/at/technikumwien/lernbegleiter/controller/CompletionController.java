package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api")
@RestController
public class CompletionController extends BaseController {
  @Autowired
  private CompletionService completionService;

  @GetMapping("student/{studentUuid}/learningModuleStudent")
  public Set<CompletionService.ClassCompletion> loadAllCompletions(@PathVariable String studentUuid) {
    return completionService.getAll(studentUuid);
  }

  @GetMapping("student/{studentUuid}/completion/at")
  public Set<LearningModuleStudentDto> loadCompletionsFor(@PathVariable String studentUuid) {
    return null;
  }

  @PostMapping("learningModuleStudent/{learningModuleStudentUuid}")
  public void markLearningModuleStudentAsComplete(
    @PathVariable String learningModuleStudentUuid,
    @RequestParam(defaultValue = "true") boolean complete) {
    if (complete) {
      completionService.markLearningModuleStudentAsComplete(learningModuleStudentUuid);
    } else {
      completionService.markLearningModuleStudentAsIncomplete(learningModuleStudentUuid);
    }
  }
}
