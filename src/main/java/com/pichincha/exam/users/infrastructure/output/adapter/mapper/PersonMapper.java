package com.pichincha.exam.users.infrastructure.output.adapter.mapper;

import com.pichincha.exam.models.PersonRequest;
import com.pichincha.exam.models.PersonResponse;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "address", source = "address")
    PersonResponse clientEntityToDto(com.pichincha.exam.users.infrastructure.output.repository.entity.Person person);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "address", source = "address")
    Person clientDtoToEntity(PersonRequest person);


}


