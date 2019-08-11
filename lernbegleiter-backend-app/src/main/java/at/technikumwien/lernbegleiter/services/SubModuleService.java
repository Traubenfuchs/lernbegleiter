package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.SubModuleDto;
import at.technikumwien.lernbegleiter.data.dto.converter.SubModuleConverter;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleEntity;
import at.technikumwien.lernbegleiter.repositories.modules.LearningModuleRepository;
import at.technikumwien.lernbegleiter.repositories.modules.SubModuleRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Transactional
@Service
public class SubModuleService {
    @Autowired
    private SubModuleRepository subModuleRepository;
    @Autowired
    private SubModuleConverter subModuleConverter;
    @Autowired
    private LearningModuleRepository learningModuleRepository;

    public Collection<SubModuleDto> getAllByLearningModule(@NonNull String learningModuleUuid) {
        return subModuleConverter.toDtoSet(subModuleRepository.findByParentUuid(learningModuleUuid));
    }

    public void update(
            @NonNull String subModuleUuid,
            @NonNull @Validated SubModuleDto subModuleDto
    ) {
        SubModuleEntity subModuleEntity = subModuleRepository.getOne(subModuleUuid);
        subModuleConverter.applyToEntity(subModuleDto, subModuleEntity);

        LearningModuleEntity learningModuleEntity = subModuleEntity.getParent();
        if (subModuleDto.getStart() == null) {
            subModuleEntity.setStart(learningModuleEntity.getStart());
        }
        if (subModuleDto.getDeadline() == null) {
            subModuleEntity.setDeadline(learningModuleEntity.getDeadline());
        }

        subModuleEntity.setUuid(subModuleUuid);
    }

    public UuidResponse create(
            @NonNull String learningModuleUuid,
            @NonNull @Validated SubModuleDto subModuleDto
    ) {
        LearningModuleEntity learningModuleEntity = learningModuleRepository.getOne(learningModuleUuid);

        SubModuleEntity subModuleEntity = new SubModuleEntity();
        subModuleConverter.applyToEntity(subModuleDto, subModuleEntity);
        subModuleEntity.generateUuid();
        subModuleEntity.setParent(learningModuleEntity);

        if (subModuleDto.getStart() == null) {
            subModuleEntity.setStart(learningModuleEntity.getStart());
        }
        if (subModuleDto.getDeadline() == null) {
            subModuleEntity.setDeadline(learningModuleEntity.getDeadline());
        }

        subModuleEntity = subModuleRepository.save(subModuleEntity);

        return new UuidResponse(subModuleEntity.getUuid());
    }

    public void delete(@NonNull String subModuleUuid) {
        subModuleRepository.deleteById(subModuleUuid);
    }

    public SubModuleDto getOne(String subModuleUuid) {
        return subModuleConverter.toDTO(subModuleRepository.getOne(subModuleUuid));
    }
}
