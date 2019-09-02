package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.QuizRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("api")
@RestController
public class QuizRunController {
    @Autowired
    private QuizRunService quizRunService;

    @PostMapping("quiz/{quizUUID}/quiz-run")
    public UuidResponse post(
            @PathVariable String quizUUID,
            @RequestBody QuizRunDto quizRunDto) {
        return quizRunService.post(quizUUID, quizRunDto);
    }

    @PutMapping("quiz-run/{quizRunUUID}")
    public void put(
            @PathVariable String quizRunUUID,
            @RequestBody QuizRunDto quizRunDto) {
        quizRunDto.setUuid(quizRunUUID);
        quizRunService.put(quizRunDto);
    }

    @GetMapping("quiz/{quizUUID}/quiz-runs")
    public Collection<QuizRunDto> getRuns(@PathVariable String quizUUID) {
        return quizRunService.getRuns(quizUUID);
    }

    @GetMapping("quiz-run/{quizRunUUID}")
    public QuizRunDto getRun(@PathVariable String quizRunUUID) {
        return quizRunService.get(quizRunUUID);
    }
}
