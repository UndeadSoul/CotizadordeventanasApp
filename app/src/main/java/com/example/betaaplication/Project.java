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

    private String deliveryAddress;

    private String startDate;

    private String projectStatus; // Renamed from status

    private String paymentStatus; // New field

    private String deposit; // New field for Abono

    private String otherWindows; // Renamed from notes

    private String otherWindowsValue; // Renamed from addedValue

    // Constructor, getters, and setters

    public Project(int clientId, String deliveryAddress, String startDate, String projectStatus, String paymentStatus, String deposit, String otherWindows, String otherWindowsValue) {
        this.clientId = clientId;
        this.deliveryAddress = deliveryAddress;
        this.startDate = startDate;
        this.projectStatus = projectStatus;
        this.paymentStatus = paymentStatus;
        this.deposit = deposit;
        this.otherWindows = otherWindows;
        this.otherWindowsValue = otherWindowsValue;
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getOtherWindows() {
        return otherWindows;
    }

    public void setOtherWindows(String otherWindows) {
        this.otherWindows = otherWindows;
    }

    public String getOtherWindowsValue() {
        return otherWindowsValue;
    }

    public void setOtherWindowsValue(String otherWindowsValue) {
        this.otherWindowsValue = otherWindowsValue;
    }
}
