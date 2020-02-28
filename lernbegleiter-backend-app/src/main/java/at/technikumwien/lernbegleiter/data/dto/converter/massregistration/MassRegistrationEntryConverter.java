package at.technikumwien.lernbegleiter.data.dto.converter.massregistration;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.entities.massregistration.*;
import org.springframework.stereotype.*;

@Component
public class MassRegistrationEntryConverter extends DtoEntityConverter<MassRegistrationEntryEntity, MassRegistrationRowDto> {
  @Override
  public void applyToDtoCustom(MassRegistrationEntryEntity entity, MassRegistrationRowDto dto) {
    dto
      .setPassword(entity.getPassword())
      .setUsername(entity.getUsername())
      .setSecret(entity.getSecret())
    ;
  }

  @Override
  public void applyToEntityCustom(MassRegistrationRowDto dto, MassRegistrationEntryEntity entity) {

  }
}
