package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.entities.*;
import at.technikumwien.lernbegleiter.repositories.*;
import com.google.common.cache.*;
import lombok.*;
import org.apache.tomcat.util.http.fileupload.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.concurrent.*;

@Service
public class ImageService {
  @Autowired
  private LobRepository lobRepository;

  @Autowired
  private HttpServletResponse httpServletResponse;

  private LoadingCache<String, CachedImage> cache;

  public ImageService() {
    cache = CacheBuilder
      .newBuilder()
      .maximumWeight(1024L * 1000L * 1000L * 20) // 20 mb
      .weigher((Weigher<String, CachedImage>) (key, value) -> value.getBytes().length)
      .build(new CacheLoader<>() {
        @Override
        public CachedImage load(String key) {
          LobEntity le = lobRepository.getOne(key);
          return new CachedImage(le.getBytes(), le.getFilename());
        }
      });
  }

  public void writeToResponse(String imageUUID) throws ExecutionException, IOException {
    CachedImage ci = cache.get(imageUUID);
    try (ByteArrayInputStream bais = new ByteArrayInputStream(ci.bytes)) {
      httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
      httpServletResponse.setHeader("Cache-Control", "31536000");
      httpServletResponse.setHeader("Content-Type", "image/jpeg");
      httpServletResponse.setHeader("Content-Disposition", "inline; filename=\"" + ci.filename + "\"");
      IOUtils.copy(bais, httpServletResponse.getOutputStream());
    }
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class CachedImage {
    private byte[] bytes;
    private String filename;
  }
}
