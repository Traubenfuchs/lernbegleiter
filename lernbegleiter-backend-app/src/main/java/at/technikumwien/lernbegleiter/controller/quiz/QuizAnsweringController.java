package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.services.quiz.QuizAnsweringService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RestController
public class QuizAnsweringController extends BaseController {
    // - answer quiz question
    // - start quiz
    //

    @Autowired
    private QuizAnsweringService quizAnsweringService;

    @GetMapping("quiz-")
    public void getAnswers() {

    }

    @PostMapping("quiz-attempt/{quizAttemptUUID}:answer")
    public void answer(
            @PathVariable String quizAttemptUUID,
            @RequestBody AnswerRequest answerRequest) {
        quizAnsweringService.answer(
                quizAttemptUUID,
                answerRequest.getQuizAnswerUuid(),
                answerRequest.getCorrect());
    }

    @Data
    public static class AnswerRequest {
        private String quizAnswerUuid;
        private Boolean correct;
    }
}