package com.example.betaaplication;

// POJO to hold the complete details of a project, including the client's name.
public class ProjectDetails {
    public int id;
    public String clientName;
    public String startDate;
    public String status;
    public String deliveryAddress;
    public String notes;
    public String addedValue;

    public ProjectDetails(int id, String clientName, String startDate, String status, String deliveryAddress, String notes, String addedValue) {
        this.id = id;
        this.clientName = clientName;
        this.startDate = startDate;
        this.status = status;
        this.deliveryAddress = deliveryAddress;
        this.notes = notes;
        this.addedValue = addedValue;
    }
}
