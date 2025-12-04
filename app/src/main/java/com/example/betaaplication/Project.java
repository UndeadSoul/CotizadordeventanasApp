package com.example.betaaplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects_table",
        foreignKeys = @ForeignKey(entity = Client.class,
                                  parentColumns = "id",
                                  childColumns = "clientId",
                                  onDelete = ForeignKey.CASCADE))
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int clientId;

    private String startDate;

    private String status;

    private String deliveryAddress;

    private String notes;

    private String addedValue;

    // Constructor, getters, and setters

    public Project(int clientId, String startDate, String status, String deliveryAddress, String notes, String addedValue) {
        this.clientId = clientId;
        this.startDate = startDate;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.addedValue = addedValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStatus() {
        return status;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getNotes() {
        return notes;
    }

    public String getAddedValue() {
        return addedValue;
    }
}
