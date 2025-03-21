package com.example.EV_Rentals.Controller;

import com.example.EV_Rentals.Entity.Ride;
import com.example.EV_Rentals.Service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides")
public class RideController {
    @Autowired
    private RideService rideService;

    // Start a ride (with userId in PathVariable)
    @PostMapping("/start/{userId}")
    public ResponseEntity<Ride> startRide(@PathVariable String userId,
                                          @RequestBody RideRequest rideRequest) {
        Ride ride = rideService.startRide(userId, rideRequest.getLatitude(), rideRequest.getLongitude());
        return ResponseEntity.ok(ride);
    }

    // End a ride
    @PostMapping("/end/{rideId}")
    public ResponseEntity<Ride> endRide(@PathVariable String rideId,
                                        @RequestBody RideRequest rideRequest) {
        Ride ride = rideService.endRide(rideId, rideRequest.getLatitude(), rideRequest.getLongitude());
        return ResponseEntity.ok(ride);
    }
}

// DTO for handling request body
class RideRequest {
    private double latitude;
    private double longitude;

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
