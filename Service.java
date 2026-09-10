package com.dispatch.service;

import com.dispatch.exception.*;
import com.dispatch.model.*;

import java.util.*;

public class DispatchEngine {
    private final Map<String, Ambulance> ambulances = new HashMap<>();
    private final PriorityQueue<EmergencyRequest> waitingQueue;
    private final List<EmergencyHistoryRecord> historyLog = new ArrayList<>();

    public DispatchEngine() {
        // High priority ranks (lower numbers) are sorted first
        this.waitingQueue = new PriorityQueue<>(Comparator.comparingInt(r -> r.getPriority().getRank()));
    }

    public void registerAmbulance(Ambulance ambulance) {
        ambulances.put(ambulance.getAmbulanceId(), ambulance);
    }

    public String submitEmergencyRequest(EmergencyRequest request) throws InvalidEmergencyRequestException {
        if (request.getPatientId() == null || request.getEmergencyType() == null) {
            throw new InvalidEmergencyRequestException("Invalid emergency application metadata.");
        }
        
        Ambulance allocated = findBestAmbulance(request);
        if (allocated != null) {
            assignAmbulance(request, allocated);
            return request.getAssignedAmbulanceId();
        } else {
            request.setStatus("QUEUED");
            waitingQueue.add(request);
            return null;
        }
    }

    private Ambulance findBestAmbulance(EmergencyRequest request) {
        Ambulance bestMatch = null;
        double minDistance = Double.MAX_VALUE;

        for (Ambulance amb : ambulances.values()) {
            if (amb.getState() == AmbulanceState.AVAILABLE && amb.getType() == request.getRequiredAmbulanceType()) {
                double dist = calculateDistance(amb.getCurrentX(), amb.getCurrentY(), request.getPickupX(), request.getPickupY());
                if (dist < minDistance) {
                    minDistance = dist;
                    bestMatch = amb;
                }
            }
        }
        return bestMatch;
    }

    private void assignAmbulance(EmergencyRequest request, Ambulance ambulance) {
        ambulance.setState(AmbulanceState.DISPATCHED);
        request.setAssignedAmbulanceId(ambulance.getAmbulanceId());
        request.setStatus("DISPATCHED");
        
        double distance = calculateDistance(ambulance.getCurrentX(), ambulance.getCurrentY(), request.getPickupX(), request.getPickupY());
        double eta = (distance / 50.0) * 60.0; // Assume average speed of 50 km/h

        historyLog.add(new EmergencyHistoryRecord(request.getRequestId(), request.getPatientId(), ambulance.getAmbulanceId(), distance, eta));
    }

    public void transitionState(String ambulanceId, AmbulanceState newState) throws AmbulanceUnavailableException {
        Ambulance amb = ambulances.get(ambulanceId);
        if (amb == null) throw new AmbulanceUnavailableException("Ambulance configuration parameters invalid.");
        
        amb.setState(newState);
        if (newState == AmbulanceState.AVAILABLE) {
            processWaitingQueue();
        }
    }

    private void processWaitingQueue() {
        if (waitingQueue.isEmpty()) return;

        List<EmergencyRequest> reEvaluateList = new ArrayList<>();
        while (!waitingQueue.isEmpty()) {
            reEvaluateList.add(waitingQueue.poll());
        }

        for (EmergencyRequest req : reEvaluateList) {
            Ambulance allocated = findBestAmbulance(req);
            if (allocated != null) {
                assignAmbulance(req, allocated);
            } else {
                waitingQueue.add(req);
            }
        }
    }

    public double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public List<EmergencyHistoryRecord> getHistoryLog() { return historyLog; }
    public PriorityQueue<EmergencyRequest> getWaitingQueue() { return waitingQueue; }
    public Map<String, Ambulance> getAmbulances() { return ambulances; }
}
