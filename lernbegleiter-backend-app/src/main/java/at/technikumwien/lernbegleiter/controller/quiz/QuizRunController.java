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

    @GetMapping({"quiz-run-admin/{quizRunUUID}"})
    public QuizRunDto getRunAdmin(@PathVariable String quizRunUUID) throws ExecutionException {
        QuizRunDto result = quizRunService.getCached(quizRunUUID);
        return result;
    }

    @GetMapping({"quiz-run-student/{quizRunUUID}"})
    public QuizRunDto getRunStudent(@PathVariable String quizRunUUID) throws ExecutionException {
        QuizRunDto result = quizRunService.getCached(quizRunUUID);
        result.getCurrentQuestion().getAnswers().stream().forEach(a -> a.setCorrect(null));
        return result;
    }

    @PostMapping("quiz-run/{quizRunUUID}:advance")
    public QuizRunDto advance(@PathVariable String quizRunUUID) throws ExecutionException {
        authHelper.isAdminOrTeacherOrThrow();
        quizRunService.advance(quizRunUUID);
        return getRunAdmin(quizRunUUID);
    }
}
