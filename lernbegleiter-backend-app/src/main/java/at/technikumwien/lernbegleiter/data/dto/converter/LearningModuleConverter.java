package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import org.springframework.stereotype.*;

import java.util.stream.*;

@Component
public class LearningModuleConverter extends DtoEntityConverter<LearningModuleEntity, LearningModuleDto> {
  @Override
  public void applyToDtoCustom(LearningModuleEntity learningModuleEntity, LearningModuleDto learningModuleDto) {
    learningModuleDto
      .setLobs(learningModuleEntity.getLobs().stream().map(lobEntity -> new LobDto().setFilename(lobEntity.getLob().getFilename())
        .setUuid(lobEntity.getLob().getUuid())).collect(Collectors.toList()))
      .setUuid(learningModuleEntity.getUuid())
      .setName(learningModuleEntity.getName())
      .setDeadline(learningModuleEntity.getDeadline())
      .setStart(learningModuleEntity.getStart())
      .setColor(learningModuleEntity.getColor())
      .setDescription(learningModuleEntity.getDescription())
    ;

    learningModuleEntity.getClazz().getLobs().stream().filter(x -> x.getVisibleForModules()).forEach(lob -> learningModuleDto.getLobs().add(new LobDto().setFilename(lob.getLob().getFilename()).setUuid(lob.getLob().getUuid()).setVisibleForModules(lob.getVisibleForModules())));
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
