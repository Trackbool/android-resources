package com.example.mvp_tests;

import java.util.ArrayList;
import java.util.List;

public class ModelMockImpl implements MainScreenContract.Model {
    List<String> clients;

    public ModelMockImpl(){
        clients = new ArrayList<>();
        clients.add("Adri");
        clients.add("Marta");
        clients.add("Pepe");
        clients.add("Irene");
    }

    @Override
    public List<String> getClients() {
        return clients;
    }
}
