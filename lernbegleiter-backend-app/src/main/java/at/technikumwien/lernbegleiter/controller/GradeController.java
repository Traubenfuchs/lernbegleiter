package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RequestMapping("api")
@RestController
public class GradeController extends BaseController {
  @Autowired
  private GradeService gradeService;
  @Autowired
  private GradeImportService gradeImportService;

  @GetMapping("grades")
  public Set<GradeDto> getAll() {
    authHelper.isAdminOrTeacherOrThrow();
    return gradeService.getAll();
  }

  @GetMapping("grade/{gradeUuid}")
  public GradeDto get(@PathVariable String gradeUuid) {
    authHelper.isAdminOrTeacherOrThrow();
    return gradeService.getOne(gradeUuid);
  }

  @PostMapping("grade")
  public UuidResponse createGrade(@Valid @RequestBody CreateGradeRequest createGradeRequest) {
    authHelper.isAdminOrTeacherOrThrow();
    return new UuidResponse(gradeService.create(createGradeRequest));
  }

  @PostMapping("grade/{targetUuid}/import/{sourceUuid}")
  public void importGrade(
    @PathVariable String targetUuid,
    @PathVariable String sourceUuid
  ) {
    authHelper.isAdminOrTeacherOrThrow();
    gradeImportService.importToGradeFrom(targetUuid, sourceUuid);
  }

  @DeleteMapping("grade/{uuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteGrade(@PathVariable String uuid) {
    authHelper.isAdminOrTeacherOrThrow();
    gradeService.delete(uuid);
  }

  @DeleteMapping("grade/{gradeUuid}/student/{studentUuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteStudentFromGrade(
    @PathVariable String gradeUuid,
    @PathVariable String studentUuid
  ) {
    authHelper.isAdminOrTeacherOrThrow();
    gradeService.deleteStudentFromGrade(studentUuid, gradeUuid);
  }
}
