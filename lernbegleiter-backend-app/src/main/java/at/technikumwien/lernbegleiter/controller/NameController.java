package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.services.user.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.*;

@AllArgsConstructor
@RestController
public class NameController {
  private final MassRegistrationService massRegistrationService;

  @GetMapping("unsecured-api/random-name")
  public ResponseEntity<?> getOne(
    @RequestParam(defaultValue = "1") Integer amount,
    @RequestParam(name = "list-format", defaultValue = "rows") ListFormat listFormat) {
    if (listFormat == ListFormat.json) {
      return ResponseEntity.ok(IntStream.range(0, amount).mapToObj(i -> massRegistrationService.generateName()).collect(Collectors.toList()));
    }

    if (listFormat == ListFormat.rows) {
      StringBuilder result = new StringBuilder();
      result.append(massRegistrationService.generateName());

      for (int i = 1; i < amount; i++) {
        result.append("\r\n");
        result.append(massRegistrationService.generateName());
      }

      return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "text/plain").body(result.toString());
    }

    throw new RuntimeException("ListFormat <" + listFormat + "> not supported");

  }

  enum ListFormat {
    json,
    rows
  }
}
