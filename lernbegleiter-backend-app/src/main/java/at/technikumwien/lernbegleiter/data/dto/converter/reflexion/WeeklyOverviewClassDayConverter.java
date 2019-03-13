package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDayDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassDayEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewClassDayConverter extends DtoEntityConverter<WeeklyOverviewClassDayEntity, WeeklyOverviewClassDayDto> {
  @Override
  public WeeklyOverviewClassDayDto toDTO(WeeklyOverviewClassDayEntity weeklyOverviewClassDayEntity) {
    return null;
  }

  @Override
  public WeeklyOverviewClassDayEntity toEntity(WeeklyOverviewClassDayDto weeklyOverviewClassDayDto) {
    return null;
  }
}
