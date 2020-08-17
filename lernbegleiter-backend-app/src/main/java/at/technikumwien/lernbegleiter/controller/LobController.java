package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.services.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.concurrent.*;

@RequestMapping("api")
@RestController
@AllArgsConstructor
public class LobController {
  private final LobService lobService;

  @GetMapping("lob/{lobUUID}")
  public void get(@PathVariable String lobUUID) throws IOException, ExecutionException {
    lobService.writeToResponse(lobUUID);
  }
}
