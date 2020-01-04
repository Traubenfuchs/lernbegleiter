package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class MoodleQuizController extends BaseController {
  @Autowired
  private MoodleXmlService moodleXmlService;

  @PostMapping("quiz/moodle")
  public UuidResponse postMoodle(@RequestBody MoodleXmlBase64 moodleXmlBase64) throws Exception {
    authHelper.isAdminOrTeacherOrThrow();
    return moodleXmlService.post(
      moodleXmlBase64.getName(),
      moodleXmlBase64.getBase64()
    );
  }

  @Data
  static class MoodleXmlBase64 {
    private String base64;
    private String name;
  }
}
