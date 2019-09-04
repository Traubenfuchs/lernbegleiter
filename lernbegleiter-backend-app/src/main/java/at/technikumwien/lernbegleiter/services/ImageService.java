package at.technikumwien.lernbegleiter.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
  @Autowired
  private LobService lobService;
  private  LoadingCache<String, CachedImage> cache;

  public ImageService() {

    cache = CacheBuilder
        .newBuilder()
        .maximumWeight(1024L*1000L*1000L*20) // 20 mb
        .weigher((Weigher<String, CachedImage>) (key, value) -> value.getBytes().length)
        .build(new CacheLoader<>() {
          @Override
          public CachedImage load(String key) {
            return null;
          }
        });
  }

  public void getImageResponse(String uuid) {
  }

  @Getter
  @Setter
  @AllArgsConstructor
  static class CachedImage {
    private byte[] bytes;
    private String filename;
  }
}
