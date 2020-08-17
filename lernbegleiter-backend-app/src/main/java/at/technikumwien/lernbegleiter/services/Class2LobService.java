package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@AllArgsConstructor
public class Class2LobService {
  private final Class2LobRepository class2LobRepository;
  private ClassRepository classRepository;
  private final static Base64.Decoder decoder = Base64.getDecoder();
  private final UserRepository userRepository;

  public LobDto save(String classUUID, LobDto lobDto) {
    return new LobDto()
      .setUuid(class2LobRepository.save(
        new Class2LobEntity().setClassEntity(classRepository.getOne(classUUID))
          .setLob(new LobEntity()
            .setFilename(lobDto.getFilename())
            .setBytes(decoder.decode(lobDto.getBase64String()))
            .setOwner(userRepository.getCurrentUser())
          )
        ).getUuid()
      );
  }

  public void delete(String lobUUID) {
  }
}
