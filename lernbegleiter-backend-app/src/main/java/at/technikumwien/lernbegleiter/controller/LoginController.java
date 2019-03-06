package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.LoginRequest;
import at.technikumwien.lernbegleiter.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.Filter;
import java.util.List;

@RestController
public class LoginController {
  @Autowired
  private LoginService loginService;

  @PostMapping("api/login")
  public Object login(@RequestBody LoginRequest loginRequest) {
    return loginService.login(loginRequest);
  }
}
