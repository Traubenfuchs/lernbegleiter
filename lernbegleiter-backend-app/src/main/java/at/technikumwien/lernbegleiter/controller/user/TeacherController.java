package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
