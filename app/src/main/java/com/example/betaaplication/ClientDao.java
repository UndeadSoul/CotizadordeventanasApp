package com.example.betaaplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    void insert(Client client);

    @Query("SELECT * FROM clients_table ORDER BY name ASC")
    LiveData<List<Client>> getAllClients();

    @Query("SELECT * FROM clients_table WHERE id = :clientId")
    LiveData<Client> getClientById(int clientId);
}
