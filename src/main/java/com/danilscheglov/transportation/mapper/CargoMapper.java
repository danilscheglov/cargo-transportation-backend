package com.danilscheglov.transportation.mapper;

import com.danilscheglov.transportation.dto.CargoDto;
import com.danilscheglov.transportation.entity.Cargo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CargoMapper {

    Cargo fromDto(CargoDto dto);

    CargoDto toDto(Cargo user);

}
