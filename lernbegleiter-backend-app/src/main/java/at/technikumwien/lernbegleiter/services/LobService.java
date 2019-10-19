package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import lombok.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import javax.annotation.*;
import java.security.*;

@Transactional
@Service
public class LobService {
  @Autowired
  private LobRepository lobRepository;

  public UuidResponse store(String filename, @NonNull byte[] bytes) throws NoSuchAlgorithmException {
    LobEntity lobEntity = new LobEntity()
      .generateUuid()
      .setBytes(bytes)
      .setFilename(filename);

    if (StringUtils.isEmpty(filename)) {
      lobEntity.setFilename(lobEntity.getUuid() + ".jpg");
    }

    return new UuidResponse(lobRepository.save(lobEntity).getUuid());
  }

  public LobEntity get(@NonNull String uuid) {
    return lobRepository.getOne(uuid);
  }

  /**
   * If the given LobDto is not null and the quizPictureBase64 inside is not empty,
   * the image is stored in the DB and set to the lob field in the given entity.
   *
   * @param lob
   * @param entityWithLob
   */
  public void applyImage(@Nullable LobDto lob, @NonNull EntityWithLob<?> entityWithLob) {
    if (lob == null) {
      return;
    }

    if (StringUtils.isEmpty(lob.getQuizPictureBase64())) {
      return;
    }

    LobEntity le = new LobEntity(lob.getQuizPictureBase64())
      .setFilename(lob.getQuizPictureFileName())
      .setFkOwnerUuid(AuthHelper.getCurrentUserUUIDOrThrow());

    le = lobRepository.save(le);

    entityWithLob.setLob(le);
  }
}
