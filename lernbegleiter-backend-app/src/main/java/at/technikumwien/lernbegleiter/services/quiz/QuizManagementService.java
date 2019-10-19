package at.technikumwien.lernbegleiter.services.quiz;

import at.technikumwien.lernbegleiter.data.dto.converter.quiz.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.quiz.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.quiz.*;
import at.technikumwien.lernbegleiter.services.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import javax.validation.*;
import java.util.*;

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
