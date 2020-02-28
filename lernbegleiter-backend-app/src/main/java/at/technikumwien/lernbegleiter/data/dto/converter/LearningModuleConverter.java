package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import org.springframework.stereotype.*;

@Component
public class LearningModuleConverter extends DtoEntityConverter<LearningModuleEntity, LearningModuleDto> {
  @Override
  public void applyToDtoCustom(LearningModuleEntity learningModuleEntity, LearningModuleDto learningModuleDto) {
    learningModuleDto
      .setUuid(learningModuleEntity.getUuid())
      .setName(learningModuleEntity.getName())
      .setDeadline(learningModuleEntity.getDeadline())
      .setStart(learningModuleEntity.getStart())
      .setColor(learningModuleEntity.getColor())
      .setDescription(learningModuleEntity.getDescription())
    ;
  }

  @Override
  public void applyToEntityCustom(LearningModuleDto learningModuleDto, LearningModuleEntity learningModuleEntity) {
    learningModuleEntity
      .setUuid(learningModuleDto.getUuid())
      .setName(learningModuleDto.getName())
      .setDeadline(learningModuleDto.getDeadline())
      .setStart(learningModuleDto.getStart())
      .setColor(learningModuleDto.getColor())
      .setDescription(learningModuleDto.getDescription())
    ;
  }
}
