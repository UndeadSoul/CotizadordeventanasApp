package com.example.betaaplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Query("SELECT p.id as projectId, c.name as clientName, p.startDate as startDate FROM projects_table as p JOIN clients_table as c ON p.clientId = c.id ORDER BY p.startDate DESC")
    LiveData<List<ProjectWithClientName>> getAllProjectsWithClientName();

    @Transaction
    @Query("SELECT p.id, c.name as clientName, p.startDate, p.status, p.deliveryAddress, p.notes, p.addedValue FROM projects_table p JOIN clients_table c ON p.clientId = c.id WHERE p.id = :projectId")
    LiveData<ProjectDetails> getProjectDetailsById(int projectId);

    @Query("SELECT * FROM projects_table ORDER BY startDate DESC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM projects_table WHERE clientId = :clientId ORDER BY startDate DESC")
    LiveData<List<Project>> getProjectsForClient(int clientId);
}
