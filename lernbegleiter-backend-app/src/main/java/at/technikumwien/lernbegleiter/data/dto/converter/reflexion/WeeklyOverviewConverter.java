package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;

@Component
public class WeeklyOverviewConverter extends DtoEntityConverter<WeeklyOverviewEntity, WeeklyOverviewDto> {
  @Autowired
  private WeeklyOverviewReflectionClassConverter weeklyOverviewReflectionClassConverter;
  @Autowired
  private WeeklyOverviewClassConverter weeklyOverviewClassConverter;

  @Override
  public void applyToDto(WeeklyOverviewEntity weeklyOverviewEntity, WeeklyOverviewDto weeklyOverviewDto) {
    LocalDate firstDayOfWeek = LocalDate.of(weeklyOverviewEntity.getYear(), 2, 1)
      .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weeklyOverviewEntity.getCalendarWeek())
      .with(ChronoField.DAY_OF_WEEK, DayOfWeek.MONDAY.getValue());
    LocalDate lastDayOfWeek = firstDayOfWeek.with(ChronoField.DAY_OF_WEEK, DayOfWeek.FRIDAY.getValue());

    weeklyOverviewDto
      .setFurtherSteps(weeklyOverviewEntity.getFurtherSteps())
      .setCalendarWeek(weeklyOverviewEntity.getCalendarWeek())
      .setYear(weeklyOverviewEntity.getYear())
      .setMyWeeklyGoals(weeklyOverviewEntity.getMyWeeklyGoals())
      .setReflexionClasses(weeklyOverviewReflectionClassConverter.toDtoList(weeklyOverviewEntity.getWeeklyOVerviewReflectionClasses()))
      .setUuid(weeklyOverviewEntity.getUuid())
      .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toDtoList(weeklyOverviewEntity.getWeeklyOverviewClasses()))
      .setFirstDayOfWeek(firstDayOfWeek)
      .setLastDayOfWeek(lastDayOfWeek)
    ;

    weeklyOverviewDto.getReflexionClasses().sort(Comparator.comparing(WeeklyOverviewReflectionClassDto::getName));
    weeklyOverviewDto.getWeeklyOverviewClasses().sort(Comparator.comparing(WeeklyOverviewClassDto::getName));
  }

  @Override
  public void applyToEntity(WeeklyOverviewDto weeklyOverviewDto, WeeklyOverviewEntity weeklyOverviewEntity) {
    weeklyOverviewEntity
      .setFurtherSteps(weeklyOverviewDto.getFurtherSteps())
      .setCalendarWeek(weeklyOverviewDto.getCalendarWeek())
      .setYear(weeklyOverviewEntity.getYear())
      .setMyWeeklyGoals(weeklyOverviewDto.getMyWeeklyGoals())
      .setWeeklyOVerviewReflectionClasses(weeklyOverviewReflectionClassConverter.toEntitySet(weeklyOverviewDto.getReflexionClasses()))
      .setUuid(weeklyOverviewDto.getUuid())
      .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toEntitySet(weeklyOverviewDto.getWeeklyOverviewClasses()));
  }
}
