package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.services.massregistration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api")
@RestController
public class MassRegistrationController extends BaseController {
  @Autowired
  private MassRegistrationService massRegistrationService;

  @GetMapping("mass-registration/{massRegistrationUUID}")
  public MassRegistrationDto getAll(@PathVariable String massRegistrationUUID) {
    return massRegistrationService.get(massRegistrationUUID);
  }

  @PatchMapping("mass-registration/{massRegistrationUUID}")
  public void patch(
    @PathVariable String massRegistrationUUID,
    @RequestBody MassRegistrationDto massRegistrationDto) {
    massRegistrationService.patch(massRegistrationUUID, massRegistrationDto);
  }

  @DeleteMapping("mass-registration/{massRegistrationUUID}")
  public void patch(
    @PathVariable String massRegistrationUUID) {
    massRegistrationService.delete(massRegistrationUUID);
  }

  @GetMapping("mass-registrations")
  public Set<MassRegistrationDto> getAll() {
    return massRegistrationService.getAll();
  }

  @PostMapping("mass-registration")
  public MassRegistrationDto massRegister(
    @RequestBody MassRegistrationDto massRegistrationDto) {
    return massRegistrationService.massRegister(massRegistrationDto);
  }
}
