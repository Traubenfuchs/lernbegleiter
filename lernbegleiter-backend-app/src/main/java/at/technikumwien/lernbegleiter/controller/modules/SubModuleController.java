package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.SubModuleDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.SubModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class SubModuleController  extends BaseController {
    @Autowired
    private SubModuleService subModuleService;

    @GetMapping("sub-module/{subModuleUuid}")
    public Object getOne(@PathVariable String subModuleUuid) {  authHelper.isAdminOrTeacherOrThrow();
        return subModuleService.getOne(subModuleUuid);
    }

    @GetMapping("learning-module/{learningModuleUuid}/sub-modules")
    public Object getSubModulesByLearningModule(@PathVariable String learningModuleUuid) { authHelper.isAdminOrTeacherOrThrow();

        return subModuleService.getAllByLearningModule(learningModuleUuid);
    }

    @PostMapping("learning-module/{learningModuleUuid}/sub-module")
    public UuidResponse create(
            @PathVariable String learningModuleUuid,
            @RequestBody SubModuleDto subModuleDto
    ) { authHelper.isAdminOrTeacherOrThrow();
        return subModuleService.create(learningModuleUuid, subModuleDto);
    }

    @PatchMapping("sub-module/{subModuleUuid}")
    public void update(
            @PathVariable String subModuleUuid,
            @RequestBody SubModuleDto subModuleDto
    ) { authHelper.isAdminOrTeacherOrThrow();
        subModuleService.update(subModuleUuid, subModuleDto);
    }

    @DeleteMapping("sub-module/{subModuleUuid}")
    public void delete(
            @PathVariable String subModuleUuid
    ) { authHelper.isAdminOrTeacherOrThrow();
        subModuleService.delete(subModuleUuid);
    }
}
