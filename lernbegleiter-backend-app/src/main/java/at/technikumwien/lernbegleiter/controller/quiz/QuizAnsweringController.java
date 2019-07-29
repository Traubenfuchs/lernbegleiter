package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.controller.BaseController;
import at.technikumwien.lernbegleiter.services.quiz.QuizAnsweringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class QuizAnsweringController  extends BaseController {
    // - answer quiz question
    // - start quiz
    //

    @Autowired
    private QuizAnsweringService quizAnsweringService;
}