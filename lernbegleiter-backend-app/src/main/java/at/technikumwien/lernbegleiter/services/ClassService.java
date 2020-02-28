package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import javax.persistence.*;
import javax.validation.*;
import java.util.*;

@AllArgsConstructor
@Transactional
@Validated
@Service
public class ClassService {
  private final ClassRepository classRepository;
  private final ClassConverter classConverter;
  private final EntityManager em;

  public Collection<ClassDto> getAllForGrade(@NonNull String gradeUuid) {
    return classConverter.toDtoSet(classRepository.findByGrade(gradeUuid));
  }

  public Collection<ClassDto> getAll() {
    return classConverter.toDtoSet(classRepository.findAll());
  }

  public ClassDto getOne(@NonNull String classUuid) {
    return classConverter.toDTO(classRepository.getOne(classUuid));
  }

  public UuidResponse create(@Valid @NonNull ClassDto classDto) {
    ClassEntity classEntity = classConverter.toEntity(classDto)
      .generateUuid();
    classEntity = classRepository.save(classEntity);
    return new UuidResponse(classEntity.getUuid());
  }

  public void update(@NonNull String classUuid, @Valid @NonNull ClassDto classDto) {
    ClassEntity classEntity = classRepository.getOne(classUuid);
    classConverter.applyToEntityFull(classDto, classEntity);
  }

  public void delete(@NonNull String classUuid) {
    ClassEntity ce = classRepository.getOne(classUuid);
    ce.getModules().forEach(m -> {
      m.getLearningModuleStudents().clear();
      em.remove(m);
    });
    ce.getWeeklyOverviewClasses().forEach(woc -> {
      woc.getDays().clear();
      woc.getWeeklyOverview().getWeeklyOverviewClasses().remove(woc);
    });
    ce.getWeeklyOverviewReflectionClasses().forEach(wor -> {
      wor.getWeeklyOverview().getWeeklyOVerviewReflectionClasses().remove(wor);
    });
    em.remove(ce);
  }
}
