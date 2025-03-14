package com.pichincha.exam.users.util;

import com.pichincha.exam.models.Client;
import com.pichincha.exam.models.Person;
import com.pichincha.exam.users.domain.entity.Gender;

public class MockUtil {
    private MockUtil() {

    }

    public static Client buildClient() {
        Client client = new Client();
        client.setAddress("SAN JUAN");
        client.setNames("JOSE RODOLFO");
        client.setPassword("Jose123");
        client.setPhone("0986492314");
        client.setStatus(Boolean.TRUE);
        return client;

    }

    public static com.pichincha.exam.users.domain.entity.Client buildClientEntity() {
        com.pichincha.exam.users.domain.entity.Client client = new com.pichincha.exam.users.domain.entity.Client();
        client.setPassword("Jose123");
        client.setPersonId(1L);
        client.setStatus(Boolean.TRUE);
        client.setId(2L);
        return client;

    }

    public static com.pichincha.exam.users.domain.entity.Person buildPersonEntity() {
        com.pichincha.exam.users.domain.entity.Person person = new com.pichincha.exam.users.domain.entity.Person();
        person.setName("José Obando");
        person.setId(1L);
        person.setPhone("0986492314");
        person.setAddress("SAN PEDRO");
        person.setGender(Gender.MALE);
        person.setIdentification("1718552365");
        return person;

    }

    public static Person buildPerson() {
        Person person = new Person();
        person.setName("José Obando");
        person.setId(1L);
        person.setPhone("0986492314");
        person.setAddress("SAN PEDRO");
        person.setGender(Person.GenderEnum.MALE);
        person.setIdentification("1718552365");
        return person;

    }
}
