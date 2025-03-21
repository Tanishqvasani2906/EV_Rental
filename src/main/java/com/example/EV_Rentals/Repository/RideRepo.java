package com.example.EV_Rentals.Repository;

import com.example.EV_Rentals.Entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepo extends JpaRepository<Ride, String> {
}
