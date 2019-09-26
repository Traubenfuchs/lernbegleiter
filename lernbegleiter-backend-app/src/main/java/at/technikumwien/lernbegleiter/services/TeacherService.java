package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.TeacherDto;
import at.technikumwien.lernbegleiter.data.dto.converter.TeacherConverter;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Service
public class TeacherService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private TeacherConverter teacherConverter;

  public Collection<TeacherDto> getAll() {
    return teacherConverter.toDtoSet(userRepository.findByRightsContains("TEACHER"));
  }

  public void delete(@NonNull String teacherUuid) {
    UserEntity userEntity = userRepository.getOne(teacherUuid);
    if (!userEntity.getRights().contains("TEACHER")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to delete a user that is not a teacher.");
    }
    userRepository.delete(userEntity);
  }

  public TeacherDto get(@NonNull String teacherUuid) {
    UserEntity userEntity = userRepository.getOne(teacherUuid);
    if (!userEntity.getRights().contains("TEACHER")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to get a user that is not a teacher.");
    }
    return teacherConverter.toDTO(userEntity);
  }

  public Object create(@Valid @NonNull TeacherDto techerDto) {
    UserEntity userEntity = teacherConverter.toEntity(techerDto)
        .generateUuid()
        .setRights(Set.of("TEACHER"));
    userEntity = userRepository.save(userEntity);
    return new UuidResponse(userEntity.getUuid());
  }

  public void update(@NonNull String teacherUuid, @Valid @NonNull TeacherDto teacherDto) {
    UserEntity userEntity = userRepository.getOne(teacherUuid);
    if (!userEntity.getRights().contains("TEACHER")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to update a user that is not a teacher.");
    }

    byte[] tmpHashedAndSaltedPassword = userEntity.getHashedAndSaltedPassword();

    teacherConverter.applyToEntity(teacherDto, userEntity);

    if (StringUtils.isEmpty(teacherDto.getPassword())) {
      userEntity.setHashedAndSaltedPassword(tmpHashedAndSaltedPassword);
    }
  }
}
