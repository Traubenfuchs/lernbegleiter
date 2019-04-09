package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.ClassDto;
import at.technikumwien.lernbegleiter.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class ClassesController {
    @Autowired
    private ClassService classService;

    @GetMapping("grade/{gradeUuid}/classes")
    public Object getAllForGrade(@PathVariable String gradeUuid) {
        return classService.getAllForGrade(gradeUuid);
    }

    @GetMapping("classes")
    public Object getAllForGrade() {
        return classService.getAll();
    }

    @GetMapping("class/{classUuid}")
    public Object getOne(@PathVariable String classUuid) {
        return classService.getOne(classUuid);
    }

    @PostMapping("class")
    public Object create(@RequestBody ClassDto classDto) {
        return classService.create(classDto);
    }

    @PatchMapping("class/{classUuid}")
    public void update(
            @PathVariable String classUuid,
            @RequestBody ClassDto classDto
    ) {
        classService.update(classUuid, classDto);
    }

    @DeleteMapping("class/{classUuid}")
    public void update(
            @PathVariable String classUuid) {
        classService.delete(classUuid);
    }
}
