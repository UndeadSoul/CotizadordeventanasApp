package com.example.betaaplication;

import androidx.room.ColumnInfo;

// A POJO (Plain Old Java Object) to hold the result of a JOIN query.
public class ProjectWithClientName {

    @ColumnInfo(name = "projectId")
    public int projectId;

    @ColumnInfo(name = "clientName")
    public String clientName;

    @ColumnInfo(name = "startDate")
    public String startDate;

    @ColumnInfo(name = "projectStatus")
    public String projectStatus; // New field

    // Room uses this constructor to create objects.
    public ProjectWithClientName(int projectId, String clientName, String startDate, String projectStatus) {
        this.projectId = projectId;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectStatus = projectStatus;
    }
}
