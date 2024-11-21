package com.essobhi.bookscape.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto<D>{
    private Integer id;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer createdBy;
    private Integer lastModifiedBy;
}
