package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.entities.base.*;
import org.springframework.util.*;

import java.lang.reflect.*;
import java.util.*;

public abstract class DtoEntityConverter<ENTITY extends BaseEntity<ENTITY>, DTO extends BaseDto<DTO>> {
  private final Constructor<ENTITY> entityConstructor;
  private final Constructor<DTO> dtoConstructor;

  public DtoEntityConverter() {
    Type type = getClass().getGenericSuperclass();

    while (!(type instanceof ParameterizedType) || ((ParameterizedType) type).getRawType() != DtoEntityConverter.class) {
      if (type instanceof ParameterizedType) {
        type = ((Class<?>) ((ParameterizedType) type).getRawType()).getGenericSuperclass();
      } else {
        type = ((Class<?>) type).getGenericSuperclass();
      }
    }
    try {
      entityConstructor = ((Class<ENTITY>) ((ParameterizedType) type).getActualTypeArguments()[0]).getDeclaredConstructor();
      dtoConstructor = ((Class<DTO>) ((ParameterizedType) type).getActualTypeArguments()[1]).getDeclaredConstructor();
    } catch (Exception e1) {
      throw new RuntimeException();
    }
  }

  public void applyToDtoFull(ENTITY entity, DTO dto) {
    applyToDtoBase(entity, dto);
    applyToDtoCustom(entity, dto);
  }

  public void applyToEntityFull(DTO dto, ENTITY entity) {
    applyToEntityBase(dto, entity);
    applyToEntityCustom(dto, entity);
  }

  public void applyToDtoBase(ENTITY entity, DTO dto) {
    dto.setUuid(entity.getUuid());
    if (entity instanceof BaseEntityCreationUpdateDate) {
      dto.setTsUpdate(((BaseEntityCreationUpdateDate<?>) entity).getTsUpdate());
      dto.setTsCreation(((BaseEntityCreationUpdateDate<?>) entity).getTsCreation());
    } else if (entity instanceof BaseEntityCreationDate) {
      dto.setTsCreation(((BaseEntityCreationDate<?>) entity).getTsCreation());
    }
  }

  public void applyToEntityBase(DTO dto, ENTITY entity) {

  }

  public abstract void applyToDtoCustom(ENTITY entity, DTO dto);

  public abstract void applyToEntityCustom(DTO dto, ENTITY entity);

  public DTO toDTO(ENTITY entity) {
    if (entity == null) {
      return null;
    }
    try {
      DTO dto = dtoConstructor.newInstance();
      applyToDtoFull(entity, dto);
      return dto;
    } catch (Exception e1) {
      throw new RuntimeException(e1);
    }
  }

  public ENTITY toEntity(DTO dto) {
    if (dto == null) {
      return null;
    }
    try {
      ENTITY entity = entityConstructor.newInstance();
      applyToEntityFull(dto, entity);
      return entity;
    } catch (Exception e1) {
      throw new RuntimeException(e1);
    }
  }

  public List<DTO> toDtoList(Collection<ENTITY> entities) {
    if (entities == null) {
      return null;
    }
    ArrayList<DTO> result = new ArrayList<>();
    for (ENTITY entity : entities) {
      result.add(toDTO(entity));
    }
    return result;
  }

  public List<ENTITY> toEntityList(Collection<DTO> dtos) {
    if (dtos == null) {
      return null;
    }
    ArrayList<ENTITY> result = new ArrayList<>();
    for (DTO dto : dtos) {
      result.add(toEntity(dto));
    }
    return result;
  }

  public Set<DTO> toDtoSet(Collection<ENTITY> entities) {
    HashSet<DTO> result = new HashSet<>();
    if (entities == null) {
      return null;
    }
    for (ENTITY entity : entities) {
      result.add(toDTO(entity));
    }
    return result;
  }

  public Set<ENTITY> toEntitySet(Collection<DTO> dtos) {
    if (dtos == null) {
      return null;
    }
    HashSet<ENTITY> result = new HashSet<>();
    for (DTO dto : dtos) {
      result.add(toEntity(dto));
    }
    return result;
  }

  // TODO make this mess more beautiful
  public void applyOrCreateToEntityCollection(Collection<DTO> dtoCollection, Collection<ENTITY> entityCollection) {
    outer:
    for (DTO qqd : dtoCollection) {
      for (ENTITY qqe : entityCollection) {
        if (qqd.getUuid() != null && Objects.equals(qqd.getUuid(), qqe.getUuid())) {
          applyToEntityFull(qqd, qqe);
          continue outer;
        }
      }

      ENTITY newEntity = toEntity(qqd);

      entityCollection
        .add(newEntity);
    }

    Iterator<ENTITY> entities = entityCollection.iterator();
    outer:
    while (entities.hasNext()) {
      ENTITY entity = entities.next();
      if (StringUtils.isEmpty(entity.getUuid())) {
        continue;
      }

      for (DTO qqd : dtoCollection) {
        if (Objects.equals(entity.getUuid(), qqd.getUuid())) {
          continue outer;
        }
      }

      entities.remove();
    }
  }
}
