package com.example.betaaplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    long insert(Project project);

    @Update
    void update(Project project);

    @Query("SELECT p.id as projectId, c.name as clientName, p.startDate, p.projectStatus, " +
           "(p.otherWindowsValue + COALESCE((SELECT SUM(CAST(w.price AS REAL)) FROM windows_table w WHERE w.projectId = p.id), 0.0)) as totalPrice " +
           "FROM projects_table p JOIN clients_table c ON p.clientId = c.id " +
           "WHERE p.projectStatus != 'INCOMPLETE' " + // Filter out incomplete projects
           "ORDER BY " +
           "    CASE p.projectStatus " +
           "        WHEN 'En espera de confirmación' THEN 1 " +
           "        WHEN 'En fabricación' THEN 2 " +
           "        WHEN 'Entregado' THEN 3 " +
           "        ELSE 4 " + // Other statuses go last
           "    END ASC, " +
           "    SUBSTR(p.startDate, 7, 4) DESC, " + // Sort by Year descending
           "    SUBSTR(p.startDate, 4, 2) DESC, " + // Sort by Month descending
           "    SUBSTR(p.startDate, 1, 2) DESC, " + // Sort by Day descending
           "    c.name ASC") // Finally, sort by client name ascending
    LiveData<List<ProjectListItem>> getProjectListItems();

    @Query("SELECT p.id as projectId, p.startDate, p.projectStatus, " +
           "(p.otherWindowsValue + COALESCE((SELECT SUM(CAST(w.price AS REAL)) FROM windows_table w WHERE w.projectId = p.id), 0.0)) as totalPrice " +
           "FROM projects_table p WHERE p.clientId = :clientId " +
           "ORDER BY p.startDate DESC")
    LiveData<List<ClientProjectListItem>> getClientProjectListItems(int clientId);

    @Transaction
    @Query("SELECT p.id, p.clientId, c.name as clientName, p.deliveryAddress, p.startDate, p.projectStatus, p.paymentStatus, p.deposit, p.otherWindows, p.otherWindowsValue FROM projects_table p JOIN clients_table c ON p.clientId = c.id WHERE p.id = :projectId")
    LiveData<ProjectDetails> getProjectDetailsById(long projectId);

    @Query("SELECT * FROM projects_table WHERE projectStatus = 'INCOMPLETE'")
    List<Project> getIncompleteProjects();

    @Query("DELETE FROM projects_table WHERE id IN (:projectIds)")
    void deleteProjectsByIds(List<Long> projectIds);

    @Query("SELECT * FROM projects_table ORDER BY startDate DESC")
    LiveData<List<Project>> getAllProjects();

    @Query("SELECT * FROM projects_table WHERE clientId = :clientId ORDER BY startDate DESC")
    LiveData<List<Project>> getProjectsForClient(int clientId);
}
