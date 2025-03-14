package com.pichincha.exam.users.helper.mapper;

import com.pichincha.exam.models.Client;
import com.pichincha.exam.users.domain.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mappings({
            @Mapping(target = "names", source = "person.name"),
            @Mapping(target = "address", source = "person.address"),
            @Mapping(target = "phone", source = "person.phone"),
            @Mapping(target = "status", source = "client.status")
    })
    Client clientEntityToDto(Person person, com.pichincha.exam.users.domain.entity.Client client);


}


