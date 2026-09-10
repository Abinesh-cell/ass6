package com.dispatch.model;

import java.util.UUID;

public class EmergencyRequest {
    private final String requestId;
    private final String patientId;
    private final String emergencyType;
    private final EmergencyPriority priority;
    private final double pickupX;
    private final double pickupY;
    private final double destinationX;
    private final double destinationY;
    private final AmbulanceType requiredAmbulanceType;
    private String assignedAmbulanceId;
    private String status;

    public EmergencyRequest(String patientId, String emergencyType, EmergencyPriority priority,
                            double pickupX, double pickupY, double destinationX, double destinationY,
                            AmbulanceType requiredAmbulanceType) {
        this.requestId = UUID.randomUUID().toString();
        this.patientId = patientId;
        this.emergencyType = emergencyType;
        this.priority = priority;
        this.pickupX = pickupX;
        this.pickupY = pickupY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
        this.requiredAmbulanceType = requiredAmbulanceType;
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getRequestId() { return requestId; }
    public String getPatientId() { return patientId; }
    public String getEmergencyType() { return emergencyType; }
    public EmergencyPriority getPriority() { return priority; }
    public double getPickupX() { return pickupX; }
    public double getPickupY() { return pickupY; }
    public double getDestinationX() { return destinationX; }
    public double getDestinationY() { return destinationY; }
    public AmbulanceType getRequiredAmbulanceType() { return requiredAmbulanceType; }
    public String getAssignedAmbulanceId() { return assignedAmbulanceId; }
    public void setAssignedAmbulanceId(String assignedAmbulanceId) { this.assignedAmbulanceId = assignedAmbulanceId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
