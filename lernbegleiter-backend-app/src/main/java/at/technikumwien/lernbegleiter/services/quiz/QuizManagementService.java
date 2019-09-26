package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.quiz.QuizConverter;
import at.technikumwien.lernbegleiter.data.dto.converter.quiz.QuizConverterDeep;
import at.technikumwien.lernbegleiter.data.dto.quiz.QuizDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.quiz.QuizEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import at.technikumwien.lernbegleiter.repositories.quiz.QuizRepository;
import at.technikumwien.lernbegleiter.services.LobService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Set;

@Validated
@Transactional
@Service
public class QuizManagementService {
  @Autowired
  private QuizConverterDeep quizConverterDeep;
  @Autowired
  private QuizConverter quizConverter;
  @Autowired
  private QuizRepository quizRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private LobService lobService;

  public Set<QuizDto> getAllQuizzes() {
    return quizConverter.toDtoSet(quizRepository.findAll());
  }

  public void put(@NonNull @Valid QuizDto quizDto) {
    QuizEntity qe = quizRepository.getOne(quizDto.getUuid());
    quizConverterDeep.applyToEntity(quizDto, qe);
  }


  public UuidResponse post(@NonNull @Valid QuizDto quizDto) {
    QuizEntity qe = quizConverterDeep.toEntity(quizDto);
    qe.setAuthor(userRepository.getCurrentUser());
    qe = quizRepository.save(qe);
    return new UuidResponse(qe.getUuid());
  }

  public void delete(String quizUuid) {
    quizRepository.deleteById(quizUuid);
  }

  public QuizDto get(String quizUuid) {
    return quizConverterDeep.toDTO(quizRepository.getOne(quizUuid));
  }
}
