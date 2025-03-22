package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.PenaltyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PenaltyRepo extends JpaRepository<PenaltyHistory,String> {
    @Query(value = "SELECT * FROM penalty_history WHERE vehicle_id = :vehicleId", nativeQuery = true)
    List<PenaltyHistory> findPenaltyByVehicleId(@Param("vehicleId") String vehicleId);
}

