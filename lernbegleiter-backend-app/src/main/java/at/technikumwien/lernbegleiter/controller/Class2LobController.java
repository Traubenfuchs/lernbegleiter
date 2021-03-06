package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.services.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
@AllArgsConstructor
public class Class2LobController extends BaseController {
  private final Class2LobService class2LobService;

  @PostMapping("class/{classUUID}/class-file")
  public LobDto post(@PathVariable String classUUID, @RequestBody LobDto lobDto) {
    return class2LobService.save(classUUID, lobDto);
  }

  @DeleteMapping("class-file/{uuid}")
  public void delete(@PathVariable String uuid) {
    class2LobService.delete(uuid);
  }
}
