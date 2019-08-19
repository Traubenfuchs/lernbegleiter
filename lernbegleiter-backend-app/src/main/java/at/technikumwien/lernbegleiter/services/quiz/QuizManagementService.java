package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.quiz.QuizConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.repositories.quiz.QuizRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
@Transactional
@Service
public class QuizManagementService {
    @Autowired
    private QuizConverter quizConverter;
    @Autowired
    private QuizRepository quizRepository;

    public List<QuizDto> getAllQuizzes() {
        return null;
    }

    public UuidResponse put(@NonNull @Valid QuizDto quizDto) {
        return null;
    }

    public void delete(String quizUuid) {
    }
}
