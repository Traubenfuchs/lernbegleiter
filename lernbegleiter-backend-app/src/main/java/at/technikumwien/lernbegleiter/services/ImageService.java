package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.entities.LobEntity;
import at.technikumwien.lernbegleiter.repositories.LobRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
