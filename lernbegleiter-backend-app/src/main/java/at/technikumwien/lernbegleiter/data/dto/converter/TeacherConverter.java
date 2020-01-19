package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Component
public class TeacherConverter extends DtoEntityConverter<UserEntity, TeacherDto> {
  @Autowired
  private PasswordHasher passwordHasher;

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

    if (!StringUtils.isEmpty(teacherDto.getPassword())) {
      userEntity
        .setHashedAndSaltedPassword(passwordHasher.hashAndSalt(teacherDto.getPassword()))
      ;
    }

  }
}
