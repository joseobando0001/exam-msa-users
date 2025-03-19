package com.pichincha.exam.users.infrastructure.output.adapter.mapper;

import com.pichincha.exam.models.ClientRequest;
import com.pichincha.exam.models.ClientResponse;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Client;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Person;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mappings({
            @Mapping(target = "names", source = "person.name"),
            @Mapping(target = "address", source = "person.address"),
            @Mapping(target = "phone", source = "person.phone"),
            @Mapping(target = "status", source = "client.status"),
            @Mapping(target = "userId", source = "client.id")
    })
    ClientResponse clientEntityToDto(Person person, Client client);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", expression = "java(hashingPassForEntity(client))"),
            @Mapping(target = "status", source = "client.status"),
            @Mapping(target = "personId", source = "person.id")
    })
    Client clientDtoToEntity(Person person, ClientRequest client);

    @SneakyThrows
    default String hashingPassForEntity(ClientRequest client) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return Base64.getEncoder().encodeToString(digest.digest(client.getPassword().getBytes(StandardCharsets.UTF_8)));
    }

}


