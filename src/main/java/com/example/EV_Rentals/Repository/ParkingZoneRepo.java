package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingZoneRepo extends JpaRepository<ParkingZone, String> {

    // 🔹 Find a parking zone by name (useful for admin searches)
    ParkingZone findByParkingName(String parkingName);
}
