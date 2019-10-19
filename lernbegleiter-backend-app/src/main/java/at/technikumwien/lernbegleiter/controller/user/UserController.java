package at.technikumwien.lernbegleiter.controller.user;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.services.user.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

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
