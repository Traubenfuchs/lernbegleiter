package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.StudentDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter extends DtoEntityConverter<UserEntity, StudentDto> {
    @Override
    public StudentDto toDTO(UserEntity userEntity) {
        return new StudentDto()
                .setEmail(userEntity.getEmail())
                .setFamilyName(userEntity.getFamilyName())
                .setFirstName(userEntity.getFirstName())
                .setBirthday(userEntity.getBirthday())
                ;
    }

    @Override
    public UserEntity toEntity(StudentDto studentDto) {
        return new UserEntity()
                .setEmail(studentDto.getEmail())
                .setFamilyName(studentDto.getFamilyName())
                .setFirstName(studentDto.getFirstName())
                .setBirthday(studentDto.getBirthday());
    }
}
