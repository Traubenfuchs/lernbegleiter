package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.requests.LoginRequest;
import at.technikumwien.lernbegleiter.data.responses.LoginResponse;
import at.technikumwien.lernbegleiter.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
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
