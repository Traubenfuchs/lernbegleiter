package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.quiz.QuizManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class QuizController extends BaseController {
    @Autowired
    private QuizManagementService quizManagementService;

    @GetMapping("quizzes")
    public List<QuizDto> get() {
        return quizManagementService.getAllQuizzes();
    }

    @PutMapping("quiz/{quizUuid}")
    public UuidResponse put(@RequestBody QuizDto quizDto, @PathVariable String quizUuid) {
        quizDto.setUuid(quizUuid);
        return quizManagementService.put(quizDto);
    }

    @PostMapping("quiz")
    public UuidResponse post(@RequestBody QuizDto quizDto) {
        quizDto.setUuid(null);
        return quizManagementService.put(quizDto);
    }

    @DeleteMapping("quiz/{quizUuid}")
    public void delete(@PathVariable String quizUuid) {
        quizManagementService.delete(quizUuid);
    }
}