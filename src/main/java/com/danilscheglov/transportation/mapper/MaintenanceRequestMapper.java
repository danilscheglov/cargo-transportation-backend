package com.danilscheglov.transportation.mapper;

import com.danilscheglov.transportation.dto.MaintenanceRequestDTO;
import com.danilscheglov.transportation.entity.MaintenanceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaintenanceRequestMapper {

    @Mapping(target = "createdAt", source = "fillingDate")
    MaintenanceRequest toMaintenanceRequest(MaintenanceRequestDTO maintenanceRequest);

    @Mapping(target = "fillingDate", source = "createdAt")
    MaintenanceRequestDTO toMaintenanceRequestDTO(MaintenanceRequest maintenanceRequest);

}
