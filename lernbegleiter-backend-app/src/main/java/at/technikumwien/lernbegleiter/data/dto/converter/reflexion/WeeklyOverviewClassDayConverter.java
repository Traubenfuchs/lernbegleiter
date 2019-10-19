package at.technikumwien.lernbegleiter.data.dto.converter.reflexion;

import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.dto.reflexion.*;
import at.technikumwien.lernbegleiter.entities.reflection.*;
import org.springframework.stereotype.*;

@Component
public class WeeklyOverviewClassDayConverter extends DtoEntityConverter<WeeklyOverviewClassDayEntity, WeeklyOverviewClassDayDto> {
  @Override
  public void applyToDto(WeeklyOverviewClassDayEntity weeklyOverviewClassDayEntity, WeeklyOverviewClassDayDto weeklyOverviewClassDayDto) {
    weeklyOverviewClassDayDto
      .setStudentComment(weeklyOverviewClassDayEntity.getStudentComment())
      .setTeacherComment(weeklyOverviewClassDayEntity.getTeacherComment())
      .setUuid(weeklyOverviewClassDayEntity.getUuid());
  }

  @Override
  public void applyToEntity(WeeklyOverviewClassDayDto weeklyOverviewClassDayDto, WeeklyOverviewClassDayEntity weeklyOverviewClassDayEntity) {
    weeklyOverviewClassDayEntity
      .setStudentComment(weeklyOverviewClassDayDto.getStudentComment())
      .setTeacherComment(weeklyOverviewClassDayDto.getTeacherComment())
      .setUuid(weeklyOverviewClassDayDto.getUuid());
  }
}
