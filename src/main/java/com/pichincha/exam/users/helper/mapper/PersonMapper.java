package com.pichincha.exam.users.helper.mapper;

import com.pichincha.exam.models.Person;
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
    Person clientEntityToDto(com.pichincha.exam.users.domain.entity.Person person);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "identification", source = "identification")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "address", source = "address")
    com.pichincha.exam.users.domain.entity.Person clientDtoToEntity(Person person);


}


