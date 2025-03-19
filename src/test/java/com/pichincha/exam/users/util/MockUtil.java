package com.pichincha.exam.users.util;

import com.pichincha.exam.models.ClientRequest;
import com.pichincha.exam.models.ClientResponse;
import com.pichincha.exam.models.PersonRequest;
import com.pichincha.exam.models.PersonResponse;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Client;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Gender;
import com.pichincha.exam.users.infrastructure.output.repository.entity.Person;

public class MockUtil {
    private MockUtil() {

    }

    public static ClientResponse buildClientResponse() {
        ClientResponse client = new ClientResponse();
        client.setAddress("SAN JUAN");
        client.setNames("JOSE RODOLFO");
        client.setPassword("Jose123");
        client.setPhone("0986492314");
        client.setStatus(Boolean.TRUE);
        return client;
    }

    public static ClientRequest buildClientRequest() {
        ClientRequest client = new ClientRequest();
        client.setAddress("SAN JUAN");
        client.setNames("JOSE RODOLFO");
        client.setPassword("Jose123");
        client.setPhone("0986492314");
        client.setStatus(Boolean.TRUE);
        return client;
    }


    public static Client buildClientEntity() {
        Client client = new Client();
        client.setPassword("Jose123");
        client.setPersonId(1L);
        client.setStatus(Boolean.TRUE);
        client.setId(2L);
        return client;

    }

    public static Person buildPersonEntity() {
        Person person = new Person();
        person.setName("José Obando");
        person.setId(1L);
        person.setPhone("0986492314");
        person.setAddress("SAN PEDRO");
        person.setGender(Gender.MALE);
        person.setIdentification("1718552365");
        return person;

    }

    public static PersonRequest buildPersonRequest() {
        PersonRequest person = new PersonRequest();
        person.setName("José Obando");
        person.setPhone("0986492314");
        person.setAddress("SAN PEDRO");
        person.setGender(PersonRequest.GenderEnum.MALE);
        person.setIdentification("1718552365");
        return person;
    }


    public static PersonResponse buildPersonResponse() {
        PersonResponse person = new PersonResponse();
        person.setName("José Obando");
        person.setId(1L);
        person.setPhone("0986492314");
        person.setAddress("SAN PEDRO");
        person.setGender(PersonResponse.GenderEnum.MALE);
        person.setIdentification("1718552365");
        return person;

    }
}
