package at.technikumwien.lernbegleiter.data.dto.converter.quiz;

import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class QuizConverterDeep extends QuizConverter {
  @Autowired
  private QuizQuestionConverter quizQuestionConverter;
  @Autowired
  private QuizRunConverter quizRunConverter;

  @Override
  public void applyToDto(QuizEntity quizEntity, QuizDto quizDto) {
    super.applyToDto(quizEntity, quizDto);
    quizDto
      .setQuestions(quizQuestionConverter.toDtoSet(quizEntity.getQuestions()))
      .setQuizRuns(quizRunConverter.toDtoSet(quizEntity.getQuizRuns()))
    ;
  }

  @Override
  public void applyToEntity(QuizDto quizDto, QuizEntity quizEntity) {
    super.applyToEntity(quizDto, quizEntity);

    quizQuestionConverter.applyOrCreateToEntityCollection(quizDto.getQuestions(), quizEntity.getQuestions());
    quizRunConverter.applyOrCreateToEntityCollection(quizDto.getQuizRuns(), quizEntity.getQuizRuns());

    quizEntity.getQuestions().forEach(qq -> qq.setQuiz(quizEntity));
    quizEntity.getQuizRuns().forEach(qr -> qr.setQuiz(quizEntity));
  }
}
