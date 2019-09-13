package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.services.QuizRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

@RequestMapping("api")
@RestController
public class QuizRunController {
    @Autowired
    private QuizRunService quizRunService;
    @Autowired
    private AuthHelper authHelper;

    @PostMapping("quiz/{quizUUID}/quiz-run")
    public UuidResponse post(
            @PathVariable String quizUUID,
            @RequestBody QuizRunDto quizRunDto) {
        authHelper.isAdminOrTeacherOrThrow();
        return quizRunService.post(quizUUID, quizRunDto);
    }

    @PutMapping("quiz-run/{quizRunUUID}")
    public void put(
            @PathVariable String quizRunUUID,
            @RequestBody QuizRunDto quizRunDto) {
        authHelper.isAdminOrTeacherOrThrow();
        quizRunDto.setUuid(quizRunUUID);
        quizRunService.put(quizRunDto);
    }

    @GetMapping("quiz/{quizUUID}/quiz-runs")
    public Collection<QuizRunDto> getRuns(@PathVariable String quizUUID) {
        authHelper.isAdminOrTeacherOrThrow();
        return quizRunService.getRuns(quizUUID);
    }

    @GetMapping({"quiz-run/{quizRunUUID}"})
    public QuizRunDto getRun(@PathVariable String quizRunUUID) throws ExecutionException {
        QuizRunDto result = quizRunService.getCached(quizRunUUID);

        if (authHelper.isStudent() && result.getCurrentQuestion() != null) {
            // students should not see answers (-:
            result.getCurrentQuestion().getAnswers().stream().forEach(a -> a.setCorrect(null));
        }

        return result;
    }

    @PostMapping("quiz-run/{quizRunUUID}:advance")
    public QuizRunDto advance(@PathVariable String quizRunUUID) throws ExecutionException {
        authHelper.isAdminOrTeacherOrThrow();
        quizRunService.advance(quizRunUUID);
        return getRun(quizRunUUID);
    }
}
