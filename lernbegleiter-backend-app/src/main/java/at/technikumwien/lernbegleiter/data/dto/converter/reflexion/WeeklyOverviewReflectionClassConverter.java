package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewReflectionClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewReflectionClassEntity;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewReflectionClassConverter extends DtoEntityConverter<WeeklyOverviewReflectionClassEntity, WeeklyOverviewReflectionClassDto> {
    @Override
    public WeeklyOverviewReflectionClassDto toDTO(WeeklyOverviewReflectionClassEntity weeklyOverviewReflectionClassEntity) {
        return new WeeklyOverviewReflectionClassDto()
                .setColor(weeklyOverviewReflectionClassEntity.getColor())
                .setImprovements(weeklyOverviewReflectionClassEntity.getImprovements())
                .setName(weeklyOverviewReflectionClassEntity.getName())
                .setProgress(weeklyOverviewReflectionClassEntity.getProgress())
                .setShortName(weeklyOverviewReflectionClassEntity.getShortName())
                .setUuid(weeklyOverviewReflectionClassEntity.getUuid());
    }

    @Override
    public WeeklyOverviewReflectionClassEntity toEntity(WeeklyOverviewReflectionClassDto weeklyOverviewReflectionClassDto) {
        return new WeeklyOverviewReflectionClassEntity()
                .setColor(weeklyOverviewReflectionClassDto.getColor())
                .setImprovements(weeklyOverviewReflectionClassDto.getImprovements())
                .setName(weeklyOverviewReflectionClassDto.getName())
                .setProgress(weeklyOverviewReflectionClassDto.getProgress())
                .setShortName(weeklyOverviewReflectionClassDto.getShortName())
                .setUuid(weeklyOverviewReflectionClassDto.getUuid());
    }
}
