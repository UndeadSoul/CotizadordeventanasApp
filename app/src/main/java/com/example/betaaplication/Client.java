package com.example.betaaplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clients_table")
public class Client {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String phone;

    private String address;

    private String rut;

    public Client(String name, String phone, String address, String rut) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.rut = rut;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRut() {
        return rut;
    }
}
