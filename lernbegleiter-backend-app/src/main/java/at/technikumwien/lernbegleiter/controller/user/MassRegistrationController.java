package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class MassRegistrationController extends BaseController {
  @Autowired
  private MassRegistrationService massRegistrationService;

  @PostMapping("students:mass-register")
  public MassRegistrationResponse massRegister(@RequestBody MassRegistrationRequest massRegistrationRequest) {
    return massRegistrationService.massRegister(massRegistrationRequest);
  }
}
