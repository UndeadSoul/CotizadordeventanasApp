package com.example.betaaplication;

import androidx.room.ColumnInfo;

// POJO to hold the data for a project row in the client details screen
public class ClientProjectListItem {

    @ColumnInfo(name = "projectId")
    public long projectId;

    @ColumnInfo(name = "startDate")
    public String startDate;

    @ColumnInfo(name = "projectStatus")
    public String projectStatus;

    @ColumnInfo(name = "totalPrice")
    public double totalPrice;

    public ClientProjectListItem(long projectId, String startDate, String projectStatus, double totalPrice) {
        this.projectId = projectId;
        this.startDate = startDate;
        this.projectStatus = projectStatus;
        this.totalPrice = totalPrice;
    }
}
