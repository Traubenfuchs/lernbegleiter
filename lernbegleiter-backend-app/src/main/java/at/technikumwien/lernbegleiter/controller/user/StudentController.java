package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.data.dto.StudentDto;
import at.technikumwien.lernbegleiter.services.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("students")
    public Collection<StudentDto> getAll() {
        return studentService.getAll();
    }

    @GetMapping("student/{userUuid}")
    public StudentDto get(@PathVariable String userUuid) {
        return studentService.get(userUuid);
    }

    @DeleteMapping("student/{userUuid}")
    public void delete(@PathVariable String userUuid) {
        studentService.delete(userUuid);
    }

    @PostMapping("student")
    public Object create(@RequestBody StudentDto studentDto) {
        return studentService.create(studentDto);
    }

    @PatchMapping("student/{userUuid}")
    public void update(
            @PathVariable String userUuid,
            @RequestBody StudentDto studentDto
    ) {
        studentService.update(userUuid, studentDto);
    }
}
