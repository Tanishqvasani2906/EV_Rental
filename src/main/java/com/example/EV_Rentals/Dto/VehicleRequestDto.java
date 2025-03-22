package com.example.EV_Rentals.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequestDto {
    private String type;
    private Double currentLatitude;
    private Double currentLongitude;
    private Integer batteryPercentage;
    private String parkingZoneId;
//    private String rideStartTime;
//    private String expectedReturnTime;

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

    public String getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(String parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
}
