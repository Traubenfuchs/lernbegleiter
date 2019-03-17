package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewClassConverter extends DtoEntityConverter<WeeklyOverviewClassEntity, WeeklyOverviewClassDto> {
  @Autowired
  private WeeklyOverviewClassDayConverter weeklyOverviewClassDayConverter;

  @Override
  public WeeklyOverviewClassDto toDTO(WeeklyOverviewClassEntity weeklyOverviewClassEntity) {
    return new WeeklyOverviewClassDto()
            .setColor(weeklyOverviewClassEntity.getColor())
            .setDays(weeklyOverviewClassDayConverter.toDtoSet(weeklyOverviewClassEntity.getDays()))
            .setName(weeklyOverviewClassEntity.getName())
            .setUuid(weeklyOverviewClassEntity.getUuid());
  }

  @Override
  public WeeklyOverviewClassEntity toEntity(WeeklyOverviewClassDto weeklyOverviewClassDto) {
    return new WeeklyOverviewClassEntity()
            .setColor(weeklyOverviewClassDto.getColor())
            .setDays(weeklyOverviewClassDayConverter.toEntitySet(weeklyOverviewClassDto.getDays()))
            .setName(weeklyOverviewClassDto.getName())
            .setUuid(weeklyOverviewClassDto.getUuid());
  }
}
