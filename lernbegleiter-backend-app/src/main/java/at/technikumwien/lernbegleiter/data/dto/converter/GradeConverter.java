package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.GradeDto;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GradeConverter extends DtoEntityConverter<GradeEntity, GradeDto> {
@Autowired
private StudentConverter studentConverter;
    @Override
    public GradeDto toDTO(GradeEntity gradeEntity) {
       return new GradeDto()
               .setUuid(gradeEntity.getUuid())
               .setName(gradeEntity.getName())
               .setStudents(studentConverter.toDtoSet(gradeEntity.getStudents()))
               .setUuid(gradeEntity.getUuid())
               ;
    }

    @Override
    public GradeEntity toEntity(GradeDto gradeDto) {
        return new GradeEntity()
                .setName(gradeDto.getName());
    }
}
