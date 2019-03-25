package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.components.PasswordHasher;
import at.technikumwien.lernbegleiter.data.dto.StudentDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import at.technikumwien.lernbegleiter.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter extends DtoEntityConverter<UserEntity, StudentDto> {
  @Autowired
  private PasswordHasher passwordHasher;
  @Autowired
  private GradeRepository gr;

  @Override
  public void applyToDto(UserEntity ue, StudentDto sd) {
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
  public void applyToEntity(StudentDto sd, UserEntity ue) {
    ue
        .setUuid(sd.getUuid())
        .setEmail(sd.getEmail())
        .setFamilyName(sd.getFamilyName())
        .setFirstName(sd.getFirstName())
        .setBirthday(sd.getBirthday())
        .setGrade(sd.getGradeName() == null ? null : gr.getByName(sd.getGradeName()))
    ;

    if (sd.getPassword() != null) {
      ue.setHashedAndSaltedPassword(passwordHasher.hashAndSalt(sd.getPassword()));
    }
  }
}
