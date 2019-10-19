package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.concurrent.*;


@RequestMapping("api")
@RestController
public class ImageController {
  @Autowired
  private ImageService imageService;

  @GetMapping("image/{imageUUID}")
  public void getImage(@PathVariable String imageUUID) throws IOException, ExecutionException {
    imageService.writeToResponse(imageUUID);
  }
}
