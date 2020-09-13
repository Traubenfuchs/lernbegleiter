package at.technikumwien.lernbegleiter.controller.modules;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.services.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
@AllArgsConstructor
public class LearningModule2LobController extends BaseController {
  private final LearningModule2LobService learningModule2LobService;

  @PostMapping("learning-module/{learningModuleUUID}/learning-module-file")
  public LobDto post(@PathVariable String learningModuleUUID, @RequestBody LobDto lobDto) {
    return learningModule2LobService.save(learningModuleUUID, lobDto);
  }

  @DeleteMapping("learning-module-file/{lobUUID}")
  public void delete(@PathVariable String lobUUID) {
    learningModule2LobService.delete(lobUUID);
  }
}
