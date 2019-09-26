package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.LearningModuleDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.LearningModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class LearningModuleController extends BaseController {
  @Autowired
  private LearningModuleService learningModuleService;

  @GetMapping("class/{classUuid}/learning-modules")
  public Object getAllByClass(@PathVariable String classUuid) {
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
