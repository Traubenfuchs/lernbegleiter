package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.stereotype.*;

@Component
public class WeeklyOverviewReflectionClassConverter extends DtoEntityConverter<WeeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto> {
  @Override
  public void applyToDtoCustom(WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto) {
    weeklyOverviewReflectionClassDto
      .setImprovements(weeklyOverviewReflectionClassEntity.getImprovements())
      .setName(weeklyOverviewReflectionClassEntity.getClazz().getName())
      .setProgress(weeklyOverviewReflectionClassEntity.getProgress())
      .setShortName(weeklyOverviewReflectionClassEntity.getClazz().getName()) // TODO
      .setColor(weeklyOverviewReflectionClassEntity.getClazz().getColor())
      .setUuid(weeklyOverviewReflectionClassEntity.getUuid())
    ;
  }

  @Override
  public void applyToEntityCustom(WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto, WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity) {
    weeklyOverviewReflectionClassEntity
      .setImprovements(weeklyOverviewReflectionClassDto.getImprovements())
      .setProgress(weeklyOverviewReflectionClassDto.getProgress())
      .setUuid(weeklyOverviewReflectionClassDto.getUuid());
  }
}
