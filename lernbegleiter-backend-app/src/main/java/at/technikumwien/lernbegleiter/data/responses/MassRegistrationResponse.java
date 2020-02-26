package at.technikumwien.lernbegleiter.data.responses;

import lombok.*;
import lombok.experimental.*;

import java.util.*;

@Data
@Accessors(chain = true)
public class MassRegistrationResponse {
  private Set<MassRegistrationResponseRow> users = new HashSet<>();

  public MassRegistrationResponse addRow(MassRegistrationResponseRow row) {
    users.add(row);
    return this;
  }
}