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
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("grades")
    public Set<GradeDto> getAll() {
        return gradeService.getAll();
    }

    @GetMapping("grade/{uuid}")
    public GradeDto get(@PathVariable String uuid) {
        return gradeService.getOne(uuid);
    }

    @PostMapping("grade")
    public UuidResponse createGrade(@Valid @RequestBody CreateGradeRequest createGradeRequest) {
        return new UuidResponse(gradeService.create(createGradeRequest));
    }

    @DeleteMapping("grade/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrade(@PathVariable String uuid) {
        gradeService.delete(uuid);
    }

    @DeleteMapping("grade/{gradeUuid}/student/{studentUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentFromGrade(
            @PathVariable String gradeUuid,
            @PathVariable String studentUuid
    ) {
        gradeService.deleteStudentFromGrade(studentUuid, gradeUuid);
    }
}
