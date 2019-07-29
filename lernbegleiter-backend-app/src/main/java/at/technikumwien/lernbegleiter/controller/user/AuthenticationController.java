package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.requests.LoginRequest;
import at.technikumwien.lernbegleiter.data.responses.LoginResponse;
import at.technikumwien.lernbegleiter.services.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController  extends BaseController {
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
