package com.essobhi.bookscape.mapper;

import com.essobhi.bookscape.domain.BaseEntity;
import com.essobhi.bookscape.dto.BaseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BaseMapper <E extends BaseEntity<E>, D extends BaseDto<D>> {
    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public D toDto(E entity) {
        if (entity == null) return null;
        return modelMapper.map(entity, dtoClass);
    }

    public E toEntity(D dto) {
        if (dto == null) return null;
        return modelMapper.map(dto, entityClass);
    }

    public List<D> toDtoList(List<E> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<E> toEntityList(List<D> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
