package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDto;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewReflectionClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class WeeklyOverviewConverter extends DtoEntityConverter<WeeklyOverviewEntity, WeeklyOverviewDto> {
    @Autowired
    private WeeklyOverviewReflectionClassConverter weeklyOverviewReflectionClassConverter;
    @Autowired
    private WeeklyOverviewClassConverter weeklyOverviewClassConverter;

    @Override
    public void applyToDto(WeeklyOverviewEntity weeklyOverviewEntity, WeeklyOverviewDto weeklyOverviewDto) {
        weeklyOverviewDto
                .setFurtherSteps(weeklyOverviewEntity.getFurtherSteps())
                .setCalendarWeek(weeklyOverviewEntity.getCalendarWeek())
                .setYear(weeklyOverviewEntity.getYear())
                .setMyWeeklyGoals(weeklyOverviewEntity.getMyWeeklyGoals())
                .setReflexionClasses(weeklyOverviewReflectionClassConverter.toDtoList(weeklyOverviewEntity.getReflexionClasses()))
                .setUuid(weeklyOverviewEntity.getUuid())
                .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toDtoList(weeklyOverviewEntity.getWeeklyOverviewClasses()));

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
