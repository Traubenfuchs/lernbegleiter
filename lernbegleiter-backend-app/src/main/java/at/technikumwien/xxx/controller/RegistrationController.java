package at.technikumwien.xxx.controller;

import at.technikumwien.xxx.data.RegistrationRequest;
import at.technikumwien.xxx.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
  @Autowired
  private RegistrationService registrationService;

  @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
  @PostMapping("api/registration")
  public String register(@RequestBody RegistrationRequest registrationRequest) {
    return registrationService.register(registrationRequest);
  }
}
