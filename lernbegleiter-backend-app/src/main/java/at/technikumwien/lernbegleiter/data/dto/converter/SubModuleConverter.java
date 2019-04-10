package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.SubModuleDto;
import at.technikumwien.lernbegleiter.entities.modules.SubModuleEntity;
import org.springframework.stereotype.Component;

@Component
public class SubModuleConverter extends DtoEntityConverter<SubModuleEntity, SubModuleDto> {
    @Override
    public void applyToDto(SubModuleEntity subModuleEntity, SubModuleDto subModuleDto) {
        subModuleDto
                .setName(subModuleEntity.getName())
                .setUuid(subModuleEntity.getUuid())
                .setDeadline(subModuleEntity.getDeadline())
                .setDescription(subModuleEntity.getDescription())
        ;
    }

    @Override
    public void applyToEntity(SubModuleDto subModuleDto, SubModuleEntity subModuleEntity) {
        subModuleEntity
                .setName(subModuleDto.getName())
                .setUuid(subModuleDto.getUuid())
                .setDeadline(subModuleDto.getDeadline())
                .setDescription(subModuleDto.getDescription())
        ;
    }
}
