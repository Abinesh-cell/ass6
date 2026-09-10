package com.dispatch.model;

public class Ambulance {
    private final String ambulanceId;
    private final AmbulanceType type;
    private AmbulanceState state;
    private final String driverDetails;
    private double currentX;
    private double currentY;

    public Ambulance(String ambulanceId, AmbulanceType type, String driverDetails, double currentX, double currentY) {
        this.ambulanceId = ambulanceId;
        this.type = type;
        this.driverDetails = driverDetails;
        this.state = AmbulanceState.AVAILABLE;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    // Getters and Setters
    public String getAmbulanceId() { return ambulanceId; }
    public AmbulanceType getType() { return type; }
    public AmbulanceState getState() { return state; }
    public void setState(AmbulanceState state) { this.state = state; }
    public String getDriverDetails() { return driverDetails; }
    public double getCurrentX() { return currentX; }
    public double getCurrentY() { return currentY; }
    public void setLocation(double x, double y) { this.currentX = x; this.currentY = y; }
}
