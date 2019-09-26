package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.ClassDto;
import at.technikumwien.lernbegleiter.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class ClassesController extends BaseController {
  @Autowired
  private ClassService classService;

  @GetMapping("grade/{gradeUuid}/classes")
  public Object getAllForGrade(@PathVariable String gradeUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return classService.getAllForGrade(gradeUuid);
  }

  @GetMapping("classes")
  public Object getAllForGrade() {
    authHelper.isAdminOrTeacherOrThrow();
    return classService.getAll();
  }

  @GetMapping("class/{classUuid}")
  public Object getOne(@PathVariable String classUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return classService.getOne(classUuid);
  }

  @PostMapping("class")
  public Object create(@RequestBody ClassDto classDto) {
    authHelper.isAdminOrTeacherOrThrow();
    return classService.create(classDto);
  }

  @PatchMapping("class/{classUuid}")
  public void update(
      @PathVariable String classUuid,
      @RequestBody ClassDto classDto
  ) {
    authHelper.isAdminOrTeacherOrThrow();
    classService.update(classUuid, classDto);
  }

  @DeleteMapping("class/{classUuid}")
  public void update(
      @PathVariable String classUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    classService.delete(classUuid);
  }
}
