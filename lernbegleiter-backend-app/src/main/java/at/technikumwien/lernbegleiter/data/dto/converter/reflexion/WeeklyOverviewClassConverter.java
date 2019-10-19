package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class WeeklyOverviewClassConverter extends DtoEntityConverter<WeeklyOverviewClassEntity, WeeklyOverviewClassDto> {
  @Autowired
  private WeeklyOverviewClassDayConverter weeklyOverviewClassDayConverter;

  @Override
  public void applyToDto(WeeklyOverviewClassEntity weeklyOverviewClassEntity, WeeklyOverviewClassDto weeklyOverviewClassDto) {
    weeklyOverviewClassDto
      .setColor(weeklyOverviewClassEntity.getColor())
      .setDays(weeklyOverviewClassDayConverter.toDtoList(weeklyOverviewClassEntity.getDaysOrdered()))
      .setName(weeklyOverviewClassEntity.getClazz().getName())
      .setUuid(weeklyOverviewClassEntity.getUuid());

  }

  @Override
  public void applyToEntity(WeeklyOverviewClassDto weeklyOverviewClassDto, WeeklyOverviewClassEntity weeklyOverviewClassEntity) {
    weeklyOverviewClassEntity
      .setColor(weeklyOverviewClassDto.getColor())
      .setDays(weeklyOverviewClassDayConverter.toEntitySet(weeklyOverviewClassDto.getDays()))
      .setUuid(weeklyOverviewClassDto.getUuid());
  }
}
