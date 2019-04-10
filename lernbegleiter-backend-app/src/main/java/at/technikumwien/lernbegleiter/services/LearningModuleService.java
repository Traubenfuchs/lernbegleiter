package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleDto;
import at.technikumwien.lernbegleiter.data.dto.converter.LearningModuleConverter;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import at.technikumwien.lernbegleiter.repositories.ClassRepository;
import at.technikumwien.lernbegleiter.repositories.modules.LearningModuleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

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

    public void update(
            @NonNull String learningModuleUuid,
            @NonNull @Validated LearningModuleDto learningModuleDto
    ) {
        LearningModuleEntity learningModuleEntity = learningModuleRepository.getOne(learningModuleUuid);
        learningModuleConverter.applyToEntity(learningModuleDto, learningModuleEntity);
        learningModuleEntity.setUuid(learningModuleUuid);
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
}
