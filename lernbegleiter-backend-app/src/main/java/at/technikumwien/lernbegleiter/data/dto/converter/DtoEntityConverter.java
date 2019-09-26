package at.technikumwien.lernbegleiter.data.dto.converter;

import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import at.technikumwien.lernbegleiter.entities.base.BaseEntity;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationDate;
import at.technikumwien.lernbegleiter.entities.base.BaseEntityCreationUpdateDate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

  public void applyToDto(ENTITY entity, DTO dto) {
    dto.setUuid(entity.getUuid());
    if (entity instanceof BaseEntityCreationUpdateDate) {
      dto.setTsUpdate(((BaseEntityCreationUpdateDate<?>) entity).getTsUpdate());
      dto.setTsCreation(((BaseEntityCreationUpdateDate<?>) entity).getTsCreation());
    } else if (entity instanceof BaseEntityCreationDate) {
      dto.setTsCreation(((BaseEntityCreationDate<?>) entity).getTsCreation());
    }
  }

  public abstract void applyToEntity(DTO dto, ENTITY entity);

  public DTO toDTO(ENTITY entity) {
    if (entity == null) {
      return null;
    }
    try {
      DTO dto = dtoConstructor.newInstance();
      applyToDto(entity, dto);
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
      applyToEntity(dto, entity);
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
          applyToEntity(qqd, qqe);
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
