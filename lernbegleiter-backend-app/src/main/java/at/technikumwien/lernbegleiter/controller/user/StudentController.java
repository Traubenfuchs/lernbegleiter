package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api")
public class StudentController extends BaseController {
  @Autowired
  private StudentService studentService;

  @GetMapping("students")
  public Collection<StudentDto> getAll() {
    authHelper.isAdminOrTeacherOrThrow();
    return studentService.getAll();
  }

  @GetMapping("student/{userUuid}")
  public StudentDto get(@PathVariable String userUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return studentService.get(userUuid);
  }

  @DeleteMapping("student/{userUuid}")
  public void delete(@PathVariable String userUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    studentService.delete(userUuid);
  }

  @PostMapping("student")
  public Object create(@RequestBody StudentDto studentDto) {
    authHelper.isAdminOrTeacherOrThrow();
    return studentService.create(studentDto);
  }

  @PatchMapping("student/{userUuid}")
  public void update(
    @PathVariable String userUuid,
    @RequestBody StudentDto studentDto
  ) {
    authHelper.isAdminOrTeacherOrThrow();
    studentService.update(userUuid, studentDto);
  }
}
