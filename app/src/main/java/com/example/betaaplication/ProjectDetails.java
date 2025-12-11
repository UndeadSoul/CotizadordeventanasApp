package com.example.betaaplication;

// POJO to hold the complete details of a project, including the client's name.
public class ProjectDetails {
    public long id;
    public int clientId;
    public String clientName;
    public String deliveryAddress;
    public String startDate;
    public String projectStatus;
    public String paymentStatus;
    public String deposit;
    public String otherWindows;
    public String otherWindowsValue;

    // Getter methods to access the fields
    public long getId() { return id; }
    public int getClientId() { return clientId; }
    public String getClientName() { return clientName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public String getStartDate() { return startDate; }
    public String getProjectStatus() { return projectStatus; }
    public String getPaymentStatus() { return paymentStatus; }
    public String getDeposit() { return deposit; }
    public String getOtherWindows() { return otherWindows; }
    public String getOtherWindowsValue() { return otherWindowsValue; }

    public ProjectDetails(long id, int clientId, String clientName, String deliveryAddress, String startDate, String projectStatus, String paymentStatus, String deposit, String otherWindows, String otherWindowsValue) {
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
