package com.example.betaaplication;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {
    @PrimaryKey
    @NonNull
    public String object;
    public String value;

    public Data(@NonNull String object, String value){
        this.object = object;
        this.value = value;
    }
}
