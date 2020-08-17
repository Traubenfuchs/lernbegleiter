package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.components.*;
import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

import javax.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.*;
import java.util.concurrent.*;

@AllArgsConstructor
@Transactional
@Service
public class LobService {
  private final LobRepository lobRepository;
  private final HttpServletResponse httpServletResponse;

  public void writeToResponse(String uuid) throws ExecutionException, IOException {
    LobEntity lob = lobRepository.getOne(uuid);
    byte[] bytes = lob.getBytes();
    try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
      //httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
      httpServletResponse.setHeader("Cache-Control", "31536000");
      // httpServletResponse.setHeader("Content-Type", "image/jpeg");
      httpServletResponse.setHeader("Content-Disposition", "inline; filename=\"" + lob.getFilename() + "\"");
      IOUtils.copy(bais, httpServletResponse.getOutputStream());
    }
  }

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
  public void applyImage(@Nullable QuizLobDto lob, @NonNull EntityWithLob<?> entityWithLob) {
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
