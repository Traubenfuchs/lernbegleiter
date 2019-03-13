package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewReflectionClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewReflectionClassEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewRelectionClassConverter extends DtoEntityConverter<WeeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto> {
  @Override
  public WeeklyOverviewReflectionClassDto toDTO(WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity) {
    return null;
  }

  @Override
  public WeeklyOverviewReflectionClassEntity toEntity(WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto) {
    return null;
  }
}
