package ru.kpfu.itis.baigulova.server.service;

import ru.kpfu.itis.baigulova.server.model.Client;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationService {
    private List<Client> clients = new ArrayList<Client>(){
        {add(new Client("test1", "pass1"));}
        {add(new Client("test2", "pass2"));}
    };

    public Client authenticate(String name, String pass){
        return clients.stream()
                .filter(client -> client.getName().equals(name) && client.getPassword().equals(pass))
                .findFirst()
                .orElse(null);
    }
}
