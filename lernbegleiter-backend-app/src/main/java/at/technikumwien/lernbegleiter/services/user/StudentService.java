package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.data.dto.StudentDto;
import at.technikumwien.lernbegleiter.data.dto.converter.StudentConverter;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.auth.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@Transactional
@Validated
@Service
public class StudentService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private StudentConverter studentConverter;

  public Collection<StudentDto> getAll() {
    return studentConverter.toDtoList(userRepository.findByRightsContains("STUDENT"));
  }

  public StudentDto get(@NonNull String userUuid) {
    UserEntity userEntity = userRepository.getOne(userUuid);
    if (!userEntity.getRights().contains("STUDENT")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to get a user that is not a student.");
    }
    return studentConverter.toDTO(userEntity);
  }

  public void delete(@NonNull String userUuid) {
    UserEntity userEntity = userRepository.getOne(userUuid);
    if (!userEntity.getRights().contains("STUDENT")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to delete a user that is not a student.");
    }
    userRepository.delete(userEntity);
  }

  public UuidResponse create(@Valid @NonNull StudentDto studentDto) {
    UserEntity userEntity = studentConverter.toEntity(studentDto)
        .generateUuid()
        .setRights(Set.of("STUDENT"));
    userEntity = userRepository.save(userEntity);
    return new UuidResponse(userEntity.getUuid());
  }

  public void update(@NonNull String userUuid, @Valid @NonNull StudentDto studentDto) {
    UserEntity userEntity = userRepository.getOne(userUuid);
    if (!userEntity.getRights().contains("STUDENT")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are trying to update a user that is not a student.");
    }

    byte[] tmpHashedAndSaltedPassword = userEntity.getHashedAndSaltedPassword();

    studentConverter.applyToEntity(studentDto, userEntity);

    if (StringUtils.isEmpty(studentDto.getPassword())) {
      userEntity.setHashedAndSaltedPassword(tmpHashedAndSaltedPassword);
    }
  }
}
