package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.LearningModuleDto;
import at.technikumwien.lernbegleiter.entities.modules.LearningModuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearningModuleConverter extends DtoEntityConverter<LearningModuleEntity, LearningModuleDto> {
    @Autowired
    private SubModuleConverter subModuleConverter;

    @Override
    public void applyToDto(LearningModuleEntity learningModuleEntity, LearningModuleDto learningModuleDto) {
        learningModuleDto
                .setUuid(learningModuleEntity.getUuid())
                .setName(learningModuleEntity.getName())
                .setDeadline(learningModuleEntity.getDeadline())
                .setDescription(learningModuleEntity.getDescription())
                .setLearningModules(subModuleConverter.toDtoSet(learningModuleEntity.getSubModules()))
        ;
    }

    @Override
    public void applyToEntity(LearningModuleDto learningModuleDto, LearningModuleEntity learningModuleEntity) {
        learningModuleEntity
                .setUuid(learningModuleDto.getUuid())
                .setName(learningModuleDto.getName())
                .setDeadline(learningModuleDto.getDeadline())
                .setDescription(learningModuleDto.getDescription())
        ;
    }
}
