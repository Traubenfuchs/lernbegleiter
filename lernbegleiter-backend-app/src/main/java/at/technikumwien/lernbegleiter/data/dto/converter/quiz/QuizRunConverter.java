package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizRunDto;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import org.springframework.stereotype.Component;

@Component
public class QuizRunConverter extends DtoEntityConverter<QuizRunEntity, QuizRunDto> {
    @Override
    public void applyToDto(QuizRunEntity quizRunEntity, QuizRunDto quizRunDto) {

    }

    @Override
    public void applyToEntity(QuizRunDto quizRunDto, QuizRunEntity quizRunEntity) {

    }
}
