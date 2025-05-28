package com.danilscheglov.transportation.mapper;

import com.danilscheglov.transportation.dto.CarDTO;
import com.danilscheglov.transportation.entity.Car;
import com.danilscheglov.transportation.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(target = "id", source = "carDTO.id")
    @Mapping(target = "driver", source = "driver")
    Car toCar(CarDTO carDTO, User driver);

}
