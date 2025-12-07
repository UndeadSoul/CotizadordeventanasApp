package com.example.betaaplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VentanaDao {

    @Insert
    void insert(Ventana ventana);

    @Update
    void update(Ventana ventana);

    @Query("SELECT * FROM windows_table WHERE projectId = :projectId ORDER BY id ASC")
    LiveData<List<Ventana>> getWindowsForProject(int projectId);

    @Query("SELECT * FROM windows_table WHERE id = :windowId")
    LiveData<Ventana> getWindowById(int windowId);
}
