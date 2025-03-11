package com.pichincha.exam.users.helper.mapper;

import com.pichincha.exam.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client clientEntityToDto(com.pichincha.exam.users.domain.entity.Client client);

    com.pichincha.exam.users.domain.entity.Client clientDtoToEntity(Client client);

}
