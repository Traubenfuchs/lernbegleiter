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
    public void applyToDto(WeeklyOverviewEntity weeklyOverviewEntity, WeeklyOverviewDto weeklyOverviewDto) {
        weeklyOverviewDto
                .setFurtherSteps(weeklyOverviewEntity.getFurtherSteps())
                .setKw(weeklyOverviewEntity.getKw())
                .setMyWeeklyGoals(weeklyOverviewEntity.getMyWeeklyGoals())
                .setReflexionClasses(weeklyOverviewReflectionClassConverter.toDtoSet(weeklyOverviewEntity.getReflexionClasses()))
                .setUuid(weeklyOverviewEntity.getUuid())
                .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toDtoSet(weeklyOverviewEntity.getWeeklyOverviewClasses()));
    }

    @Override
    public void applyToEntity(WeeklyOverviewDto weeklyOverviewDto, WeeklyOverviewEntity weeklyOverviewEntity) {
        weeklyOverviewEntity
                .setFurtherSteps(weeklyOverviewDto.getFurtherSteps())
                .setKw(weeklyOverviewDto.getKw())
                .setMyWeeklyGoals(weeklyOverviewDto.getMyWeeklyGoals())
                .setReflexionClasses(weeklyOverviewReflectionClassConverter.toEntitySet(weeklyOverviewDto.getReflexionClasses()))
                .setUuid(weeklyOverviewDto.getUuid())
                .setWeeklyOverviewClasses(weeklyOverviewClassConverter.toEntitySet(weeklyOverviewDto.getWeeklyOverviewClasses()));
    }
}
