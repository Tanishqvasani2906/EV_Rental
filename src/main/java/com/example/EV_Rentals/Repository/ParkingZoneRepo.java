package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.ParkingZone;
import com.example.EV_Rentals.Entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingZoneRepo extends JpaRepository<ParkingZone, String> {

    // 🔹 Find a parking zone by name (useful for admin searches)
    ParkingZone findByParkingName(String parkingName);

}
