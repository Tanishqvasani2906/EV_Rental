package com.example.EV_Rentals.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingZone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)// UUID ensures unique ID
    @Column(name = "parking_zone_id")
    private String parkingZoneId;

    @Column(nullable = false, unique = true)
    private String parkingName;

    @Column(name = "center_latitude")
    private Double  centerLatitude;  // Changed to double for precise coordinates
    @Column(name = "center_longitude")
    private Double  centerLongitude;

    private Integer radius;  // Changed to int (meters should be numeric)
    private Integer capacity; // Changed to int (vehicle count should be numeric)

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(String parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
