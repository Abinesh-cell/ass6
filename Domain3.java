package com.dispatch.model;

import java.time.LocalDateTime;

public class EmergencyHistoryRecord {
    private final String requestId;
    private final String patientId;
    private final String ambulanceId;
    private final double estimatedDistance;
    private final double estimatedArrivalTimeMinutes;
    private final LocalDateTime timestamp;

    public EmergencyHistoryRecord(String requestId, String patientId, String ambulanceId, 
                                  double estimatedDistance, double estimatedArrivalTimeMinutes) {
        this.requestId = requestId;
        this.patientId = patientId;
        this.ambulanceId = ambulanceId;
        this.estimatedDistance = estimatedDistance;
        this.estimatedArrivalTimeMinutes = estimatedArrivalTimeMinutes;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format("[%s] Request %s allocated to Ambulance %s. Distance: %.2f km, ETA: %.2f mins", 
                timestamp, requestId, ambulanceId, estimatedDistance, estimatedArrivalTimeMinutes);
    }
}
