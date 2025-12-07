package com.example.betaaplication;

// POJO to hold the complete details of a project, including the client's name.
public class ProjectDetails {
    public int id;
    public int clientId; // Added field
    public String clientName;
    public String deliveryAddress;
    public String startDate;
    public String projectStatus;
    public String paymentStatus;
    public String deposit;
    public String otherWindows;
    public String otherWindowsValue;

    public ProjectDetails(int id, int clientId, String clientName, String deliveryAddress, String startDate, String projectStatus, String paymentStatus, String deposit, String otherWindows, String otherWindowsValue) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.deliveryAddress = deliveryAddress;
        this.startDate = startDate;
        this.projectStatus = projectStatus;
        this.paymentStatus = paymentStatus;
        this.deposit = deposit;
        this.otherWindows = otherWindows;
        this.otherWindowsValue = otherWindowsValue;
    }
}
