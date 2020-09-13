package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.entities.modules.*;
import at.technikumwien.lernbegleiter.repositories.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.modules.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
@AllArgsConstructor
public class LearningModule2LobService {
  private final static Base64.Decoder decoder = Base64.getDecoder();
  private final LearningModule2LobRepository learningModule2LobRepository;
  private final LearningModuleRepository learningModuleRepository;
  private final UserRepository userRepository;
  private final LobRepository lobRepository;

  public LobDto save(String learningModuleUUID, LobDto lobDto) {
    return new LobDto()
      .setUuid(learningModule2LobRepository.save(
        new LearningModule2LobEntity().setLearningModule(learningModuleRepository.getOne(learningModuleUUID))
          .setLob(new LobEntity()
            .setFilename(lobDto.getFilename())
            .setBytes(decoder.decode(lobDto.getBase64String()))
            .setOwner(userRepository.getCurrentUser())
          )
        ).getUuid()
      );
  }

  @Transactional
  public void delete(String lobUUID) {
    LobEntity lobEntity = lobRepository.getOne(lobUUID);
    learningModule2LobRepository.delete(learningModule2LobRepository.findByFkLobUuid(lobEntity.getUuid()));
  }
}
