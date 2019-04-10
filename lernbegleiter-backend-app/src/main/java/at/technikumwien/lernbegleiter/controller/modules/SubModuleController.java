package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.data.dto.SubModuleDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.SubModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class SubModuleController {
    @Autowired
    private SubModuleService subModuleService;

    @GetMapping("sub-module/{subModuleUuid}")
    public Object getOne(@PathVariable String subModuleUuid) {
        return subModuleService.getOne(subModuleUuid);
    }

    @GetMapping("learning-module/{learningModuleUuid}/sub-modules")
    public Object getSubModulesByLearningModule(@PathVariable String learningModuleUuid) {
        return subModuleService.getAllByLearningModule(learningModuleUuid);
    }

    @PostMapping("learning-module/{learningModuleUuid}/sub-module")
    public UuidResponse create(
            @PathVariable String learningModuleUuid,
            @RequestBody SubModuleDto subModuleDto
    ) {
        return subModuleService.create(learningModuleUuid, subModuleDto);
    }

    @PatchMapping("sub-module/{subModuleUuid}")
    public void update(
            @PathVariable String subModuleUuid,
            @RequestBody SubModuleDto subModuleDto
    ) {
        subModuleService.update(subModuleUuid, subModuleDto);
    }

    @DeleteMapping("sub-module/{subModuleUuid}")
    public void delete(
            @PathVariable String subModuleUuid
    ) {
        subModuleService.delete(subModuleUuid);
    }
}
