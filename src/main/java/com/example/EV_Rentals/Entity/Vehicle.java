package com.example.EV_Rentals.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // UUID for unique ID
    @Column(name = "vehicle_id")
    private String vehicleId;

    private String type; // e.g., "scooter", "car"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalStatus status = RentalStatus.AVAILABLE; // Default to "AVAILABLE"

    @Column(name = "curr_latitude")
    private Double  currentLatitude;
    @Column(name = "curr_longitude")
    private Double  currentLongitude;

    @ManyToOne
    @JoinColumn(name = "parking_zone_id")
    private ParkingZone currentParkingZone; // Null if rented

    @Column(name = "battery_percentage")
    private Integer batteryPercentage; // Battery level (0-100)

    @Column(name = "ride_start_time")
    private LocalDateTime rideStartTime;  // Time when the ride starts

    @Column(name = "expected_return_time")
    private LocalDateTime expectedReturnTime;  // Expected return deadline

    @Column(name = "actual_return_time")
    private LocalDateTime actualReturnTime;  // When the user actually returns the vehicle


    public Double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public Double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public ParkingZone getCurrentParkingZone() {
        return currentParkingZone;
    }

    public void setCurrentParkingZone(ParkingZone currentParkingZone) {
        this.currentParkingZone = currentParkingZone;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public LocalDateTime getActualReturnTime() {
        return actualReturnTime;
    }

    public void setActualReturnTime(LocalDateTime actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    public LocalDateTime getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(LocalDateTime expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public LocalDateTime getRideStartTime() {
        return rideStartTime;
    }

    public void setRideStartTime(LocalDateTime rideStartTime) {
        this.rideStartTime = rideStartTime;
    }
}

