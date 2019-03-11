package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.requests.UserUpdateRequest;
import at.technikumwien.lernbegleiter.services.UserService;
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
