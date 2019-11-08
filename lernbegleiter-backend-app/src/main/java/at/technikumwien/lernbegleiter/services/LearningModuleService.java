package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import at.technikumwien.lernbegleiter.repositories.*;
import at.technikumwien.lernbegleiter.repositories.modules.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.validation.annotation.*;

import java.util.*;

@Transactional
@Validated
@Service
public class LearningModuleService {
  @Autowired
  private LearningModuleRepository learningModuleRepository;
  @Autowired
  private ClassRepository classRepository;
  @Autowired
  private LearningModuleConverter learningModuleConverter;

  public Collection<LearningModuleDto> getAllByClass(@NonNull String classUuid) {
    return learningModuleConverter.toDtoSet(learningModuleRepository.findByClazzUuid(classUuid));
  }

  public UuidResponse create(
    @NonNull String classUuid,
    @NonNull @Validated LearningModuleDto learningModuleDto
  ) {
    ClassEntity classEntity = classRepository.getOne(classUuid);

    LearningModuleEntity learningModuleEntity = new LearningModuleEntity();
    learningModuleEntity.setClazz(classEntity);
    learningModuleConverter.applyToEntity(learningModuleDto, learningModuleEntity);
    learningModuleEntity.generateUuid();
    learningModuleRepository.save(learningModuleEntity);

    return new UuidResponse(learningModuleEntity.getUuid());
  }

  public LearningModuleDto getOne(@NonNull String learningModuleUuid) {
    return learningModuleConverter.toDTO(learningModuleRepository.getOne(learningModuleUuid));
  }

  public void delete(@NonNull String learningModuleUuid) {
    learningModuleRepository.deleteById(learningModuleUuid);
  }

  public void put(LearningModuleDto learningModuleDto) {
    LearningModuleEntity learningModuleEntity = learningModuleRepository.getOne(learningModuleDto.getUuid());
    learningModuleConverter.applyToEntity(learningModuleDto, learningModuleEntity);
  }
}
