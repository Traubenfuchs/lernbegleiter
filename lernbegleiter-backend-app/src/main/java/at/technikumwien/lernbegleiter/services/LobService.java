package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.LobEntity;
import at.technikumwien.lernbegleiter.repositories.LobRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Transactional
@Service
public class LobService {
  @Autowired
  private LobRepository lobRepository;


  public UuidResponse store(@NonNull String filename, @NonNull byte[] bytes) throws NoSuchAlgorithmException {
    return new UuidResponse(lobRepository.save(
        new LobEntity()
            .setBytes(bytes)
            .setFilename(filename)
    ).getUuid());
  }

  public LobEntity get(String uuid) {
    return lobRepository.getOne(uuid);
  }
}
