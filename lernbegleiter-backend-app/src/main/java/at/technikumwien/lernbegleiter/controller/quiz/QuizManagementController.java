package at.technikumwien.lernbegleiter.controller.quiz;

import at.technikumwien.lernbegleiter.services.quiz.QuizManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class QuizManagementController {
    @Autowired
    private QuizManagementService quizManagementService;
}