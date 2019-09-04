package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


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
