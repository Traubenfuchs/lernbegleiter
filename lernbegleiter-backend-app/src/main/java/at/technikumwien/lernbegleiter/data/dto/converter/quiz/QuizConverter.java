package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.converter.TeacherConverter;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class QuizConverter extends DtoEntityConverter<QuizEntity, QuizDto> {
    @Autowired
    private QuizQuestionConverter quizQuestionConverter;
    @Autowired
    private QuizRunConverter quizRunConverter;
    @Autowired
    private TeacherConverter teacherConverter;

    @Override
    public void applyToDto(QuizEntity quizEntity, QuizDto quizDto) {
        super.applyToDto(quizEntity, quizDto);

        quizDto
                .setUuid(quizEntity.getUuid())
                .setAuthor(teacherConverter.toDTO(quizEntity.getAuthor()))
                .setDescription(quizEntity.getDescription())
                .setMaxRetries(quizEntity.getMaxRetries())
                .setName(quizEntity.getName())
        ;
    }

    @Override
    public void applyToEntity(QuizDto quizDto, QuizEntity quizEntity) {
        quizEntity
                .setUuid(quizDto.getUuid())
                // .setAuthor(quizDto.getAuthor())
                .setDescription(quizDto.getDescription())
                .setMaxRetries(quizDto.getMaxRetries())
                .setName(quizDto.getName())
        ;
    }
}
