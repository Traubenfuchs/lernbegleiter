package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.requests.UserUpdateDto;
import at.technikumwien.lernbegleiter.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/{userUuid}")
public class UserController extends BaseController {
  @Autowired
  private UserService userService;

  @DeleteMapping
  public void delete(@PathVariable String userUuid) {
    userService.deleteByUuid(userUuid);
  }

  @GetMapping
  public UserUpdateDto update(
      @PathVariable String userUuid
  ) {
    return userService.get(userUuid);
  }

  @PatchMapping
  public void update(
      @PathVariable String userUuid,
      @RequestBody UserUpdateDto userUpdateDto
  ) {
    userService.update(userUuid, userUpdateDto);
  }
}
