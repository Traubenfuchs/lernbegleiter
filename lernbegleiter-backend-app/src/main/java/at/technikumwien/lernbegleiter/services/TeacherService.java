package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;
import org.springframework.web.server.*;

import javax.validation.*;
import java.util.*;


@Transactional
@AllArgsConstructor
@Service
public class TeacherService {
  private final UserRepository userRepository;
  private final TeacherConverter teacherConverter;

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

  public Object create(@Valid @NonNull TeacherDto teacherDto) {
    UserEntity userEntity = teacherConverter.toEntity(teacherDto)
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
