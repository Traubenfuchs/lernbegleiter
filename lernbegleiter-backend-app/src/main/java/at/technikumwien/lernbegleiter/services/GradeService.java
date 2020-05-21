package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.*;

@AllArgsConstructor
@Transactional
@Validated
@Service
public class GradeService {
  private final GradeRepository gradeRepository;
  private final GradeConverter gradeConverter;
  private final EntityManager em;

  public GradeDto getOne(String uuid) {
    return gradeConverter.toDTO(gradeRepository.getOne(uuid));
  }

  public Set<GradeDto> getAll() {
    return gradeConverter.toDtoSet(gradeRepository.findAll());
  }

  public String create(CreateGradeRequest createGradeRequest) {
    return gradeRepository.save(
      new GradeEntity()
        .setName(createGradeRequest.getName())
        .generateUuid())
      .getUuid();
  }

  public void delete(String gradeUuid) {
    GradeEntity ge = gradeRepository.getOne(gradeUuid);
    
    gradeRepository.delete(ge);
  }

  public void deleteStudentFromGrade(String studentUuid, String gradeUuid) {
    GradeEntity ge = gradeRepository.getOne(gradeUuid);

    ge.setStudents(
      ge.getStudents().stream()
        .filter(student -> {
          if (student.getUuid().equals(studentUuid)) {
            student.setGrade(null);
            return false;
          }
          return true;
        })
        .collect(Collectors.toSet()));
  }
}
