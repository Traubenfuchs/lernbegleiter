package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.auth.*;
import at.technikumwien.lernbegleiter.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class StudentConverter extends DtoEntityConverter<UserEntity, StudentDto> {
  @Autowired
  private PasswordHasher passwordHasher;
  @Autowired
  private GradeRepository gr;

  @Override
  public void applyToDtoCustom(UserEntity ue, StudentDto sd) {
    sd
      .setUuid(ue.getUuid())
      .setEmail(ue.getEmail())
      .setFamilyName(ue.getFamilyName())
      .setFirstName(ue.getFirstName())
      .setBirthday(ue.getBirthday())
      .setGradeName(ue.getGrade() == null ? null : ue.getGrade().getName())
    ;
  }

  @Override
  public void applyToEntityCustom(StudentDto sd, UserEntity ue) {
    ue
      .setUuid(sd.getUuid())
      .setEmail(sd.getEmail())
      .setFamilyName(sd.getFamilyName())
      .setFirstName(sd.getFirstName())
      .setBirthday(sd.getBirthday())
      .setGrade(sd.getGradeName() == null ? null : gr.findByName(sd.getGradeName()))
    ;

    if (sd.getPassword() != null) {
      ue.setHashedAndSaltedPassword(passwordHasher.hashAndSalt(sd.getPassword()));
    }
  }
}
