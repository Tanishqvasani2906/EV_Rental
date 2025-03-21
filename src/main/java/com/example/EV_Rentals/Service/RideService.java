package com.example.EV_Rentals.Service;

import com.example.EV_Rentals.Entity.Ride;
import com.example.EV_Rentals.Entity.RideStatus;
import com.example.EV_Rentals.Entity.Users;
import com.example.EV_Rentals.Repository.RideRepo;
import com.example.EV_Rentals.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RideService {
    @Autowired
    private RideRepo rideRepository;

    @Autowired
    private UserRepo userRepository;

    // Start a new ride
    @Transactional
    public Ride startRide(String userId, double latitude, double longitude) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Ride ride = new Ride();
        ride.setUser(user);
        ride.setStartLatitude(latitude);
        ride.setStartLongitude(longitude);
        ride.setStatus(RideStatus.ONGOING); // Set status to Ongoing

        return rideRepository.save(ride);
    }

    // End a ride
    @Transactional
    public Ride endRide(String rideId, double latitude, double longitude) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        if (ride.getStatus() != RideStatus.ONGOING) {
            throw new IllegalStateException("Ride is not in progress");
        }

        ride.setEndLatitude(latitude);
        ride.setEndLongitude(longitude);
        ride.setStatus(RideStatus.COMPLETED);

        return rideRepository.save(ride);
    }

    // Get ride details
    public Ride getRideDetails(String rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));
    }
}
