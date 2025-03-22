package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RideRepo extends JpaRepository<Ride, String> {
    @Query(value = "SELECT * FROM ride WHERE user_id = :userId", nativeQuery = true)
    List<Ride> findByUserId(@Param("userId") String userId);
}
