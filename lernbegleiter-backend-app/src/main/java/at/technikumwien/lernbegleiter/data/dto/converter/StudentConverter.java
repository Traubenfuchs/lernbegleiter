package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.StudentDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.services.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter extends DtoEntityConverter<UserEntity, StudentDto> {
    @Autowired
    private PasswordHasher passwordHasher;
    @Override
    public void applyToDto(UserEntity userEntity, StudentDto studentDto) {
        studentDto
                .setUuid(userEntity.getUuid())
                .setEmail(userEntity.getEmail())
                .setFamilyName(userEntity.getFamilyName())
                .setFirstName(userEntity.getFirstName())
                .setBirthday(userEntity.getBirthday())
                ;
    }

    @Override
    public void applyToEntity(StudentDto studentDto, UserEntity userEntity) {
        userEntity
                .setUuid(studentDto.getUuid())
                .setEmail(studentDto.getEmail())
                .setFamilyName(studentDto.getFamilyName())
                .setFirstName(studentDto.getFirstName())
                .setBirthday(studentDto.getBirthday());

        if(studentDto.getPassword() != null) {
            userEntity.setHashedAndSaltedPassword(passwordHasher.hashAndSalt(studentDto.getPassword()));
        }
    }
}
