package at.technikumwien.lernbegleiter.data.dto;

import lombok.*;
import lombok.experimental.*;

import java.time.*;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MassRegistrationDto extends BaseDto<MassRegistrationDto> {
  private Set<MassRegistrationRowDto> users = new HashSet<>();
  private String name;
  private String notes;
  private Instant deletionTime;
  private Integer amount;

  public MassRegistrationDto addRow(MassRegistrationRowDto row) {
    users.add(row);
    return this;
  }
}