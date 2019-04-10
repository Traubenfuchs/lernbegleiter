package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.LearningModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class LearningModuleController {
    @Autowired
    private LearningModuleService learningModuleService;

    @GetMapping("class/{classUuid}/learning-modules")
    public Object getAllByClass(@PathVariable String classUuid) {
        return learningModuleService.getAllByClass(classUuid);
    }

    @PostMapping("class/{classUuid}/learning-module")
    public UuidResponse create(
            @PathVariable String classUuid,
            @RequestBody LearningModuleDto learningModuleDto) {
        return learningModuleService.create(classUuid, learningModuleDto);
    }

    @GetMapping("learning-module/{learningModuleUuid}")
    public LearningModuleDto getOne(
            @PathVariable String learningModuleUuid) {
        return learningModuleService.getOne(learningModuleUuid);
    }

    @DeleteMapping("learning-module/{learningModuleUuid}")
    public void delete(
            @PathVariable String learningModuleUuid) {
        learningModuleService.delete(learningModuleUuid);
    }
}