package com.essobhi.bookscape.mapper;

import com.essobhi.bookscape.domain.BaseEntity;
import com.essobhi.bookscape.dto.BaseDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<E extends BaseEntity<E>, D extends BaseDto<D>> {
    private final ModelMapper mapper = new ModelMapper();

    public BaseMapper() {
    }

    private Type parametrizeType(int index) {
        Type superClass = this.getClass().getGenericSuperclass();
        return ((ParameterizedType) superClass).getActualTypeArguments()[index];
    }

    public E toEntity(D dto) {
        return (E) this.mapper.map(dto, this.parametrizeType(0));
    }

    public D toDto(E entity) {
        return (D) this.mapper.map(entity, this.parametrizeType(1));
    }

    public List<D> toDtos(List<E> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<E> toEntities(List<D> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}