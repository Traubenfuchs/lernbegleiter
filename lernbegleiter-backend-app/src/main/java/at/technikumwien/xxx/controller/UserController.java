package at.technikumwien.xxx.controller;

import at.technikumwien.xxx.data.UserUpdateRequest;
import at.technikumwien.xxx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/{userUuid}")
public class UserController {
  @Autowired
  private UserService userService;

  @DeleteMapping
  public void delete(@PathVariable String userUuid) {
    userService.deleteByUuid(userUuid);
  }

  @PatchMapping
  public void update(
      @PathVariable String userUuid,
      @RequestBody UserUpdateRequest userUpdateRequest
  ) {
    userService.update(userUuid, userUpdateRequest);
  }
}
