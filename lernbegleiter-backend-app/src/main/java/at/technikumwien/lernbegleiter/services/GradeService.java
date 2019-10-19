package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import java.util.*;
import java.util.stream.*;

@Transactional
@Validated
@Service
public class GradeService {
  @Autowired
  private GradeRepository gradeRepository;
  @Autowired
  private GradeConverter gradeConverter;

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

  public void delete(String uuid) {
    gradeRepository.deleteById(uuid);
  }

  public void deleteStudentFromGrade(String studentUuid, String gradeUuid) {
    GradeEntity ge = gradeRepository.getOne(gradeUuid);

    ge.setStudents(
      ge.getStudents().stream()
        .filter(student -> {
          student.setGrade(null);
          return !student.getUuid().equals(studentUuid);
        })
        .collect(Collectors.toSet()));
  }
}
