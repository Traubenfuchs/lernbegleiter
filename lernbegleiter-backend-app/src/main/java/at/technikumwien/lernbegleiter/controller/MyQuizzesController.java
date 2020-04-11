package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyQuizzesController extends BaseController {
  @Autowired
  private MyQuizzesService myQuizzesService;

  @GetMapping("api/my-quizzes")
  public MyQuizzesDto get() {
    return myQuizzesService.get();
  }
}
