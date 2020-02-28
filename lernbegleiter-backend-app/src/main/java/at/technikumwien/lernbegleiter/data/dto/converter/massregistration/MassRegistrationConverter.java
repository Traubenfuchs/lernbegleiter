package at.technikumwien.lernbegleiter.data.dto.converter.massregistration;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.*;
import at.technikumwien.lernbegleiter.entities.massregistration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class MassRegistrationConverter extends DtoEntityConverter<MassRegistrationEntity, MassRegistrationDto> {
  @Autowired
  private MassRegistrationEntryConverter massRegistrationEntryConverter;

  @Override
  public void applyToDtoCustom(MassRegistrationEntity entity, MassRegistrationDto dto) {
    dto
      .setUsers(massRegistrationEntryConverter.toDtoSet(entity.getChildren()))
      .setDeletionTime(entity.getDeletionTime())
      .setName(entity.getName())
      .setNotes(entity.getNotes())
      .setAmount(entity.getAmount());
  }

  @Override
  public void applyToEntityCustom(MassRegistrationDto dto, MassRegistrationEntity entity) {
    entity
      .setDeletionTime(dto.getDeletionTime())
      .setName(dto.getName())
      .setNotes(dto.getNotes())
      .setAmount(dto.getAmount())
    ;
  }
}
