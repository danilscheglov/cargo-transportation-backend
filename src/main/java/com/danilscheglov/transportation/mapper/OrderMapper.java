package com.danilscheglov.transportation.mapper;

import com.danilscheglov.transportation.dto.OrderDTO;
import com.danilscheglov.transportation.entity.Order;
import com.danilscheglov.transportation.mapper.helper.UserMapperHelper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapperHelper.class, CarMapper.class})
public abstract class OrderMapper {

    @Autowired
    protected UserMapperHelper userMapperHelper;

    @Mapping(target = "client", expression = "java(userMapperHelper.resolveUser(orderDto.getEmail()))")
    public abstract Order toOrder(OrderDTO orderDto);

    @Mapping(target = "car", source = "car", qualifiedByName = "toCarDto")
    @Mapping(target = "email", source = "client.email")
    @Mapping(target = "client", source = "client")
    public abstract OrderDTO toOrderDTO(Order order);

}
