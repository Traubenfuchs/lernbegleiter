package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewConverter extends DtoEntityConverter<WeeklyOverviewEntity, WeeklyOverviewDto> {
  @Override
  public WeeklyOverviewDto toDTO(WeeklyOverviewEntity weeklyOverviewEntity) {
    return null;
  }

  @Override
  public WeeklyOverviewEntity toEntity(WeeklyOverviewDto weeklyOverviewDto) {
    return null;
  }
}
