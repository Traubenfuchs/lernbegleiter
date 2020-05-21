package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api")
@RestController
public class LearningModuleController extends BaseController {
  @Autowired
  private LearningModuleService learningModuleService;

  @GetMapping("class/{classUuid}/learning-modules")
  public Set<LearningModuleDto> getAllByClass(@PathVariable String classUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return learningModuleService.getAllByClass(classUuid);
  }

  @PostMapping("class/{classUuid}/learning-module")
  public UuidResponse create(
    @PathVariable String classUuid,
    @RequestBody LearningModuleDto learningModuleDto) {
    authHelper.isAdminOrTeacherOrThrow();
    return learningModuleService.create(classUuid, learningModuleDto);
  }

  @PutMapping("learning-module")
  public void put(
    @RequestBody LearningModuleDto learningModuleDto) {
    authHelper.isAdminOrTeacherOrThrow();
    learningModuleService.put(learningModuleDto);
  }

  @GetMapping("learning-module/{learningModuleUuid}")
  public LearningModuleDto getOne(
    @PathVariable String learningModuleUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return learningModuleService.getOne(learningModuleUuid);
  }

  @DeleteMapping("learning-module/{learningModuleUuid}")
  public void delete(
    @PathVariable String learningModuleUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    learningModuleService.delete(learningModuleUuid);
  }
}
