package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController extends BaseController {
  @Autowired
  private RegistrationService registrationService;

  @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
  @PostMapping("api/registration")
  public String register(@RequestBody RegistrationRequest registrationRequest) {
    return registrationService.register(registrationRequest);
  }
}
