package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import at.technikumwien.lernbegleiter.data.dto.LobDto;
import at.technikumwien.lernbegleiter.data.responses.UuidResponse;
import at.technikumwien.lernbegleiter.entities.EntityWithLob;
import at.technikumwien.lernbegleiter.entities.LobEntity;
import at.technikumwien.lernbegleiter.repositories.LobRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Nullable;
import java.security.NoSuchAlgorithmException;

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
