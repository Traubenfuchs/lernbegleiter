package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.GradeDto;
import at.technikumwien.lernbegleiter.data.requests.CreateGradeRequest;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RequestMapping("api")
@RestController
public class GradeController extends BaseController {
    @Autowired
    private GradeService gradeService;

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
