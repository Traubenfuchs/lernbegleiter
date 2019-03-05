package at.technikumwien.xxx.controller;

import at.technikumwien.xxx.data.LoginRequest;
import at.technikumwien.xxx.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
  @Autowired
  private LoginService loginService;

  @PostMapping("api/login")
  public Object login(@RequestBody LoginRequest loginRequest) {
    return null;
  }
}
