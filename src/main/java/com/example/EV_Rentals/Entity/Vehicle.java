package com.example.EV_Rentals.Entity;
import jakarta.persistence.*;
import lombok.*;


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
}

