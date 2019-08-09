package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.TeacherDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TeacherConverter extends DtoEntityConverter<UserEntity, TeacherDto> {
    @Override
    public void applyToDto(UserEntity userEntity, TeacherDto teacherDto) {
        teacherDto
                .setBirthday(userEntity.getBirthday())
                .setEmail(userEntity.getEmail())
                .setFamilyName(userEntity.getFamilyName())
                .setFirstName(userEntity.getFirstName())
                .setUuid(userEntity.getUuid())
        ;
    }

    @Override
    public void applyToEntity(TeacherDto teacherDto, UserEntity userEntity) {
        userEntity
                .setBirthday(teacherDto.getBirthday())
                .setEmail(teacherDto.getEmail())
                .setFamilyName(teacherDto.getFamilyName())
                .setFirstName(teacherDto.getFirstName())
                .setUuid(teacherDto.getUuid())
        ;
    }
}
