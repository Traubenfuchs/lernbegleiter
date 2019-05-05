package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.DtoEntityConverter;
import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewClassDto;
import at.technikumwien.lernbegleiter.entities.reflection.WeeklyOverviewClassEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeeklyOverviewClassConverter extends DtoEntityConverter<WeeklyOverviewClassEntity, WeeklyOverviewClassDto> {
    @Autowired
    private WeeklyOverviewClassDayConverter weeklyOverviewClassDayConverter;

    @Override
    public void applyToDto(WeeklyOverviewClassEntity weeklyOverviewClassEntity, WeeklyOverviewClassDto weeklyOverviewClassDto) {
        weeklyOverviewClassDto
                .setColor(weeklyOverviewClassEntity.getColor())
                .setDays(weeklyOverviewClassDayConverter.toDtoList(weeklyOverviewClassEntity.getDaysOrdered()))
                .setName(weeklyOverviewClassEntity.getClazz().getName())
                .setUuid(weeklyOverviewClassEntity.getUuid());

    }

    @Override
    public void applyToEntity(WeeklyOverviewClassDto weeklyOverviewClassDto, WeeklyOverviewClassEntity weeklyOverviewClassEntity) {
        weeklyOverviewClassEntity
                .setColor(weeklyOverviewClassDto.getColor())
                .setDays(weeklyOverviewClassDayConverter.toEntitySet(weeklyOverviewClassDto.getDays()))
                .setUuid(weeklyOverviewClassDto.getUuid());
    }
}
