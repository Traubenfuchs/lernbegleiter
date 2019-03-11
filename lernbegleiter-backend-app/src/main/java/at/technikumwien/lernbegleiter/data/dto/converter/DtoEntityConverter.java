package at.technikumwien.lernbegleiter.data.dto.converter;

import java.util.*;

public abstract class DtoEntityConverter<ENTITY, DTO> {
    public abstract DTO toDTO(ENTITY entity);

    public abstract ENTITY toEntity(DTO dto);

    public List<DTO> toDtoList(Collection<ENTITY> entities) {
        ArrayList<DTO> result = new ArrayList<>();
        for(ENTITY entity : entities) {
            result.add(toDTO(entity));
        }
        return result;
    }

    public List<ENTITY> toEntityList(Collection<DTO> dtos) {
        ArrayList<ENTITY> result = new ArrayList<>();
        for(DTO dto : dtos) {
            result.add(toEntity(dto));
        }
        return result;
    }

    public Set<DTO> toDtoSet(Collection<ENTITY> entities) {
        HashSet<DTO> result = new HashSet<>();
        for(ENTITY entity : entities) {
            result.add(toDTO(entity));
        }
        return result;
    }

    public Set<ENTITY> toEntitySet(Collection<DTO> dtos) {
        HashSet<ENTITY> result = new HashSet<>();
        for(DTO dto : dtos) {
            result.add(toEntity(dto));
        }
        return result;
    }
}
