package com.example.betaaplication;

import androidx.room.ColumnInfo;

// POJO to hold all the data needed for a single row in the main project list
public class ProjectListItem {

    @ColumnInfo(name = "projectId")
    public int projectId;

    @ColumnInfo(name = "clientName")
    public String clientName;

    @ColumnInfo(name = "startDate")
    public String startDate;

    @ColumnInfo(name = "projectStatus")
    public String projectStatus;

    @ColumnInfo(name = "totalPrice")
    public double totalPrice;

    public ProjectListItem(int projectId, String clientName, String startDate, String projectStatus, double totalPrice) {
        this.projectId = projectId;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectStatus = projectStatus;
        this.totalPrice = totalPrice;
    }
}
