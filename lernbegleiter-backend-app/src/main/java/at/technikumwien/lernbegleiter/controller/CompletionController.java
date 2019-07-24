package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleStudentDto;
import at.technikumwien.lernbegleiter.services.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("api")
@RestController
public class CompletionController {
    @Autowired
    private CompletionService completionService;

    @GetMapping("student/{studentUuid}/learningModuleStudent")
    public Set<LearningModuleStudentDto> loadAllCompletions(@PathVariable String studentUuid) {
        return completionService.getAll(studentUuid);
    }

    @GetMapping("student/{studentUuid}/completion/at")
    public Set<LearningModuleStudentDto> loadCompletionsFor(@PathVariable String studentUuid) {
        return null;
    }

    @PostMapping("learningModuleStudent/{learningModuleStudentUuid}")
    public void markLearningModuleStudentAsComplete(@PathVariable String learningModuleStudentUuid) {
        completionService.markLearningModuleStudentAsComplete(learningModuleStudentUuid);
    }

    @PostMapping("subModuleStudent/{subModuleStudentUuid}")
    public void markSubModuleStudentAsComplete(@PathVariable String subModuleStudentUuid) {
        completionService.markSubModuleStudentAsComplete(subModuleStudentUuid);
    }
}