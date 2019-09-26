package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDto;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewReflectionClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.Comparator;

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
        .setReflexionClasses(weeklyOverviewReflectionClassConverter.toDtoList(weeklyOverviewEntity.getReflexionClasses()))
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
        .setReflexionClasses(weeklyOverviewReflectionClassConverter.toEntitySet(weeklyOverviewDto.getReflexionClasses()))
        .setUuid(weeklyOverviewDto.getUuid())
        .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toEntitySet(weeklyOverviewDto.getWeeklyOverviewClasses()));
  }
}
