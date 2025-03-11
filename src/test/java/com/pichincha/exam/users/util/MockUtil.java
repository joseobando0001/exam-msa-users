package com.pichincha.exam.users.util;

import com.pichincha.exam.models.Client;

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
}
