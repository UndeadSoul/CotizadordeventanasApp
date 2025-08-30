package com.example.betaaplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoData {
    @Query("SELECT * FROM data")
    List<Data> getDataValue();

    @Query("SELECT value FROM data WHERE object= :dataObject")
    List<String> getOneDataValue(String dataObject);

    @Insert
    void addData(Data...datas);

    @Query("UPDATE data SET value= :dataValue WHERE object= :dataObject")
    void updateData(String dataObject, String dataValue);

    @Query("DELETE FROM data WHERE object= :dataObject")
    void deleteData(String dataObject);

    @Query("SELECT COUNT(*) FROM data")
    int countData();

    @Query("SELECT COUNT(*) FROM data WHERE value= :defaultvalue")
    int countDefault(String defaultvalue);
}
