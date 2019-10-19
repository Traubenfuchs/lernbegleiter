package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController extends BaseController {
  @Autowired
  private LoginService loginService;

  @PostMapping("api/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    return loginService.login(loginRequest);
  }

  @PostMapping("api/login/check")
  public void login() {
  }
}
