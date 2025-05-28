package com.danilscheglov.transportation.mapper;

import com.danilscheglov.transportation.dto.UserDto;
import com.danilscheglov.transportation.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Named("fromDto")
    User fromDto(UserDto dto);

    UserDto toDto(User user);

}
