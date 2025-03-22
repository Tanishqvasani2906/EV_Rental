package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.Vehicle;
import com.example.EV_Rentals.Entity.RentalStatus;
import com.example.EV_Rentals.Entity.ParkingZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, String> {

    // 🔹 Find vehicles by status (e.g., AVAILABLE, RENTED)
    List<Vehicle> findByStatus(RentalStatus status);

    // 🔹 Find vehicles parked in a specific parking zone
    List<Vehicle> findByCurrentParkingZone(ParkingZone parkingZone);

    // 🔹 Count vehicles in a parking zone (to check available capacity)
    long countByCurrentParkingZone(ParkingZone parkingZone);

    // 🔹 Find available vehicles (for rentals)
    List<Vehicle> findByStatusAndCurrentParkingZoneIsNotNull(RentalStatus status);
    // Fetch vehicles by Parking Zone ID
    @Query("SELECT v FROM Vehicle v WHERE v.currentParkingZone.parkingZoneId = :parkingZoneId")
    List<Vehicle> findByParkingZoneId(@Param("parkingZoneId") String parkingZoneId);
}
