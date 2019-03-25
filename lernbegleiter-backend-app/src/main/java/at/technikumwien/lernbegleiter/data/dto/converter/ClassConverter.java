package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.ClassDto;
import at.technikumwien.lernbegleiter.entities.ClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassConverter extends DtoEntityConverter<ClassEntity, ClassDto> {
    @Autowired
    private LearningModuleConverter learningModuleConverter;

    @Override
    public void applyToDto(ClassEntity classEntity, ClassDto classDto) {
        classDto
                .setUuid(classEntity.getUuid())
                .setName(classEntity.getName())
                .setLearningModules(learningModuleConverter.toDtoSet(classEntity.getModules()))
                .setUuid(classEntity.getUuid())
        ;
    }

    @Override
    public void applyToEntity(ClassDto classDto, ClassEntity classEntity) {
        classEntity
                .setUuid(classDto.getUuid())
                .setName(classDto.getName())
                .setModules(learningModuleConverter.toEntitySet(classDto.getLearningModules()))
                .setUuid(classDto.getUuid())
        ;
    }
}
