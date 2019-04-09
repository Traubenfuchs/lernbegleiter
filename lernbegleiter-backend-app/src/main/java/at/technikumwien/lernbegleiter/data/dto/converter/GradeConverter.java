package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.GradeDto;
import at.technikumwien.lernbegleiter.entities.GradeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GradeConverter extends DtoEntityConverter<GradeEntity, GradeDto> {
    @Autowired
    private StudentConverter studentConverter;

    @Autowired
    private ClassConverter classConverter;

    @Override
    public void applyToDto(GradeEntity gradeEntity, GradeDto gradeDto) {
        gradeDto
                .setUuid(gradeEntity.getUuid())
                .setName(gradeEntity.getName())
                .setStudents(studentConverter.toDtoSet(gradeEntity.getStudents()))
                .setClasses(classConverter.toDtoSet( gradeEntity.getClasses()))
                .setUuid(gradeEntity.getUuid())
        ;
    }

    @Override
    public void applyToEntity(GradeDto gradeDto, GradeEntity gradeEntity) {
        gradeEntity
                .setUuid(gradeDto.getUuid())
                .setName(gradeDto.getName())
                .setStudents(studentConverter.toEntitySet(gradeDto.getStudents()))
                .setUuid(gradeDto.getUuid())
        ;
    }
}
