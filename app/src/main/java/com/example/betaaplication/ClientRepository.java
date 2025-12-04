package com.example.betaaplication;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ClientRepository {

    private ClientDao clientDao;
    private LiveData<List<Client>> allClients;

    public ClientRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        clientDao = db.clientDao();
        allClients = clientDao.getAllClients();
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public LiveData<Client> getClientById(int clientId) {
        return clientDao.getClientById(clientId);
    }

    public void insert(Client client) {
        new Thread(() -> {
            clientDao.insert(client);
        }).start();
    }
}
