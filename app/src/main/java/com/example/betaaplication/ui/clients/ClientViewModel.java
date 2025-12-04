package com.example.betaaplication.ui.clients;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.betaaplication.Client;
import com.example.betaaplication.ClientRepository;
import java.util.List;

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository repository;
    private LiveData<List<Client>> allClients;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        repository = new ClientRepository(application);
        allClients = repository.getAllClients();
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public LiveData<Client> getClientById(int clientId) {
        return repository.getClientById(clientId);
    }

    public void insert(Client client) {
        repository.insert(client);
    }
}
