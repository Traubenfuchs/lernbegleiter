package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.ClassDto;
import at.technikumwien.lernbegleiter.data.dto.converter.ClassConverter;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.repositories.ClassRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Transactional
@Validated
@Service
public class ClassService {
  @Autowired
  private ClassRepository classRepository;
  @Autowired
  private ClassConverter classConverter;

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
    classConverter.applyToEntity(classDto, classEntity);
  }

  public void delete(@NonNull String classUuid) {
    classRepository.deleteById(classUuid);
  }
}
