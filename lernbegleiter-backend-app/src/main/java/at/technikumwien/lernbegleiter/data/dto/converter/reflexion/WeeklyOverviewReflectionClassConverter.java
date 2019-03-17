package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewReflectionClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewReflectionClassEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewReflectionClassConverter extends DtoEntityConverter<WeeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto> {
    @Override
    public void applyToDto(WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto) {
        weeklyOverviewReflectionClassDto
                .setColor(weeklyOverviewReflectionClassEntity.getColor())
                .setImprovements(weeklyOverviewReflectionClassEntity.getImprovements())
                .setName(weeklyOverviewReflectionClassEntity.getName())
                .setProgress(weeklyOverviewReflectionClassEntity.getProgress())
                .setShortName(weeklyOverviewReflectionClassEntity.getShortName())
                .setUuid(weeklyOverviewReflectionClassEntity.getUuid());
    }

    @Override
    public void applyToEntity(WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto, WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity) {
        weeklyOverviewReflectionClassDto
                .setColor(weeklyOverviewReflectionClassDto.getColor())
                .setImprovements(weeklyOverviewReflectionClassDto.getImprovements())
                .setName(weeklyOverviewReflectionClassDto.getName())
                .setProgress(weeklyOverviewReflectionClassDto.getProgress())
                .setShortName(weeklyOverviewReflectionClassDto.getShortName())
                .setUuid(weeklyOverviewReflectionClassDto.getUuid());
    }
}
