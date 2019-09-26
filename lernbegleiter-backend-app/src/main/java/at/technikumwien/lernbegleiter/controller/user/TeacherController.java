package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.TeacherDto;
import at.technikumwien.lernbegleiter.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequestMapping("api")
@RestController
public class TeacherController extends BaseController {
  @Autowired
  private TeacherService teacherService;

  @GetMapping("teachers")
  public Collection<TeacherDto> getAll() {
    authHelper.isAdminOrTeacherOrThrow();
    return teacherService.getAll();
  }

  @GetMapping("teacher/{teacherUuid}")
  public TeacherDto get(@PathVariable String teacherUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return teacherService.get(teacherUuid);
  }

  @DeleteMapping("teacher/{teacherUuid}")
  public void delete(@PathVariable String teacherUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    teacherService.delete(teacherUuid);
  }

  @PostMapping("teacher")
  public Object create(@RequestBody TeacherDto teacherDto) {
    authHelper.isAdminOrTeacherOrThrow();
    return teacherService.create(teacherDto);
  }

  @PatchMapping("teacher/{teacherUuid}")
  public void update(
      @PathVariable String teacherUuid,
      @RequestBody TeacherDto techerDto
  ) {
    authHelper.isAdminOrTeacherOrThrow();
    teacherService.update(teacherUuid, techerDto);
  }
}
