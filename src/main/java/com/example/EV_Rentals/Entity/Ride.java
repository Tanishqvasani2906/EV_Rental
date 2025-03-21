package com.example.EV_Rentals.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ride_id")
    private String rideId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id") // Assuming `User` entity exists
    private Users user;

    @Column(name = "start_latitude",nullable = false)
    private double startLatitude;

    @Column(name = "start_longitude",nullable = false)
    private double startLongitude;

    @Column(name = "end_latitude")
    private Double endLatitude; // Nullable since ride may not be completed
    @Column(name = "end_longitude")
    private Double endLongitude; // Nullable for the same reason

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RideStatus status = RideStatus.NOT_TAKEN; // Default value set

    public Double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
