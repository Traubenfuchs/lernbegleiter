package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewConverter extends DtoEntityConverter<WeeklyOverviewEntity, WeeklyOverviewDto> {
  @Autowired
  private WeeklyOverviewReflectionClassConverter weeklyOverviewReflectionClassConverter;
  @Autowired
  private WeeklyOverviewClassConverter weeklyOverviewClassConverter;

  @Override
  public WeeklyOverviewDto toDTO(WeeklyOverviewEntity weeklyOverviewEntity) {
    return new WeeklyOverviewDto()
        .setFurtherSteps(weeklyOverviewEntity.getFurtherSteps())
            .setFirstDayOfWeek(weeklyOverviewEntity.getFirstDayOfWeek())
            .setKw(weeklyOverviewEntity.getKw())
            .setLastDayOfWeek(weeklyOverviewEntity.getLastDayOfWeek())
            .setMyWeeklyGoals(weeklyOverviewEntity.getMyWeeklyGoals())
            .setReflexionClasses(weeklyOverviewReflectionClassConverter.toDtoSet(weeklyOverviewEntity.getReflexionClasses()))
            .setUuid(weeklyOverviewEntity.getUuid())
            .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toDtoSet(weeklyOverviewEntity.getWeeklyOverviewClasses()));
  }

  @Override
  public WeeklyOverviewEntity toEntity(WeeklyOverviewDto weeklyOverviewDto) {
    return new WeeklyOverviewEntity()
            .setFurtherSteps(weeklyOverviewDto.getFurtherSteps())
            .setFirstDayOfWeek(weeklyOverviewDto.getFirstDayOfWeek())
            .setKw(weeklyOverviewDto.getKw())
            .setLastDayOfWeek(weeklyOverviewDto.getLastDayOfWeek())
            .setMyWeeklyGoals(weeklyOverviewDto.getMyWeeklyGoals())
            .setReflexionClasses(weeklyOverviewReflectionClassConverter.toEntitySet(weeklyOverviewDto.getReflexionClasses()))
            .setUuid(weeklyOverviewDto.getUuid())
            .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toEntitySet(weeklyOverviewDto.getWeeklyOverviewClasses()));
  }
}
