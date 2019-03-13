package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewClassConverter extends DtoEntityConverter<WeeklyOverviewClassEntity, WeeklyOverviewClassDto> {
  @Override
  public WeeklyOverviewClassDto toDTO(WeeklyOverviewClassEntity weeklyOverviewClassEntity) {
    return null;
  }

  @Override
  public WeeklyOverviewClassEntity toEntity(WeeklyOverviewClassDto weeklyOverviewClassDto) {
    return null;
  }
}
