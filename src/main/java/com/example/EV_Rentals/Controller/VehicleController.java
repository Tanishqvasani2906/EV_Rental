package com.example.EV_Rentals.Controller;

import com.example.EV_Rentals.Dto.VehicleRequestDto;
import com.example.EV_Rentals.Entity.*;
import com.example.EV_Rentals.Repository.ParkingZoneRepo;
import com.example.EV_Rentals.Repository.PenaltyRepo;
import com.example.EV_Rentals.Repository.VehicleRepo;

import com.example.EV_Rentals.Service.PaymentService;
import com.example.EV_Rentals.Service.PenaltyService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleRepo vehicleRepository;
    @Autowired
    private ParkingZoneRepo parkingZoneRepository;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private PenaltyRepo penaltyHistoryRepo;
    @Autowired
    private PaymentService paymentService;

    // 🔹 1. Add a new vehicle (Needs a Parking Zone)
    @PostMapping("/addVehicle/{parkingZoneId}")
    public ResponseEntity<?> addVehicle(@PathVariable String parkingZoneId, @RequestBody VehicleRequestDto requestDto) {
        Optional<ParkingZone> parkingZoneOpt = parkingZoneRepository.findById(parkingZoneId);

        if (parkingZoneOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Parking Zone ID");
        }

        ParkingZone parkingZone = parkingZoneOpt.get();

        // Check if the parking zone has capacity left
        long parkedVehicles = vehicleRepository.countByCurrentParkingZone(parkingZone);
        if (parkedVehicles >= parkingZone.getCapacity()) {
            return ResponseEntity.badRequest().body("Parking Zone is full");
        }

        // Create a new vehicle
        Vehicle vehicle = new Vehicle();
        vehicle.setType(requestDto.getType());
        vehicle.setCurrentLatitude(requestDto.getCurrentLatitude());
        vehicle.setCurrentLongitude(requestDto.getCurrentLongitude());
        vehicle.setCurrentParkingZone(parkingZone);
        vehicle.setStatus(RentalStatus.AVAILABLE); // Default status
        vehicle.setBatteryPercentage(requestDto.getBatteryPercentage());

        vehicleRepository.save(vehicle);
        return ResponseEntity.ok(vehicle);
    }


    // 🔹 2. Get all vehicles (Optional filter by status)
    @GetMapping("/getAllVehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam(required = false) RentalStatus status) {
        List<Vehicle> vehicles = (status == null) ? vehicleRepository.findAll() : vehicleRepository.findByStatus(status);
        return ResponseEntity.ok(vehicles);
    }

    // 🔹 3. Get vehicle by ID
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Object> getVehicleById(@PathVariable String vehicleId) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);

        if (vehicle.isPresent()) {
            return ResponseEntity.ok(vehicle.get()); // ✅ Returns Vehicle object correctly
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // ✅ Explicitly set HTTP status
                    .body(Collections.singletonMap("error", "Vehicle not found")); // ✅ Returns JSON instead of String
        }
    }



    // 🔹 Update vehicle location (For real-time tracking)
    @PutMapping("/{vehicleId}/location")
    public ResponseEntity<?> updateVehicleLocation(@PathVariable String vehicleId, @RequestParam Double latitude, @RequestParam Double longitude) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle not found");
        }

        Vehicle vehicle = vehicleOpt.get();
        vehicle.setCurrentLatitude(latitude);
        vehicle.setCurrentLongitude(longitude);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Vehicle location updated");
    }

    // 🔹 Start a Rental Ride (Change status to RENTED)
    @PostMapping("/{vehicleId}/rent")
    public ResponseEntity<?> startRide(@PathVariable String vehicleId) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle not found");
        }

        Vehicle vehicle = vehicleOpt.get();
        if (vehicle.getStatus() != RentalStatus.AVAILABLE) {
            return ResponseEntity.badRequest().body("Vehicle is not available for rent");
        }

        vehicle.setStatus(RentalStatus.RENTED);
        vehicle.setRideStartTime(LocalDateTime.now());
        vehicle.setExpectedReturnTime(LocalDateTime.now().plusHours(1));
        vehicle.setCurrentParkingZone(null); // Remove from parking since it's being rented
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Ride started successfully");
    }
//    @PostMapping("/{vehicleId}/request-ride")
//    public ResponseEntity<ApiResponse<String>> requestRide(@PathVariable String vehicleId, Principal principal) {
//        // 🔹 Get logged-in user email from authentication
//        String userEmail = principal.getName();
//
//        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
//        if (vehicleOpt.isEmpty()) {
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse<>(false, "Vehicle not found"));
//        }
//
//        Vehicle vehicle = vehicleOpt.get();
//        if (vehicle.getStatus() != RentalStatus.AVAILABLE) {
//            return ResponseEntity.badRequest()
//                    .body(new ApiResponse<>(false, "Vehicle is not available for rent"));
//        }
//
//        vehicle.setStatus(RentalStatus.PENDING_PAYMENT);
//        vehicle.setRideStartTime(LocalDateTime.now());
//        vehicle.setExpectedReturnTime(LocalDateTime.now().plusHours(1));
//        vehicleRepository.save(vehicle);
//
//        try {
//            // 🔹 Call Razorpay Payment API
//            String orderResponse = paymentService.createPaymentOrder(vehicle.getRideStartTime(), vehicle.getExpectedReturnTime());
//            return ResponseEntity.ok(
//                    new ApiResponse<>(true, orderResponse, "Payment order created successfully")
//            );
//        } catch (RazorpayException e) {
//            return ResponseEntity.status(500)
//                    .body(new ApiResponse<>(false, "Error creating payment order: " + e.getMessage()));
//        }
//    }
//
//    @PostMapping("/{vehicleId}/confirm-ride")
//    public ResponseEntity<?> confirmRide(@PathVariable String vehicleId, @RequestParam String paymentId) {
//        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
//        if (vehicleOpt.isEmpty()) {
//            return ResponseEntity.badRequest().body("Vehicle not found");
//        }
//
//        Vehicle vehicle = vehicleOpt.get();
//        if (vehicle.getStatus() != RentalStatus.PENDING_PAYMENT) {
//            return ResponseEntity.badRequest().body("Payment not completed. Cannot start ride.");
//        }
//
//        // 🔹 Verify Payment in Razorpay
//        boolean paymentVerified = paymentService.verifyPayment(paymentId);
//        if (!paymentVerified) {
//            return ResponseEntity.badRequest().body("Payment verification failed!");
//        }
//
//        vehicle.setStatus(RentalStatus.RENTED);
//        vehicleRepository.save(vehicle);
//
//        return ResponseEntity.ok("Ride started successfully!");
//    }


    // 🔹 End a Rental Ride (Must be within a Parking Zone)
    @PostMapping("/{vehicleId}/return")
    public ResponseEntity<?> endRide(@PathVariable String vehicleId, @RequestParam String parkingZoneId) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        Optional<ParkingZone> parkingZoneOpt = parkingZoneRepository.findById(parkingZoneId);

        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle not found");
        }
        if (parkingZoneOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Parking Zone ID");
        }

        Vehicle vehicle = vehicleOpt.get();
        vehicle.setActualReturnTime(LocalDateTime.now());

        // 🚨 Fix: Ensure expectedReturnTime is NOT null
//        if (vehicle.getExpectedReturnTime() == null) {
//            return ResponseEntity.badRequest().body("Error: Expected return time is missing for this ride.");
//        }
        // Ensure expected return time is set (e.g., 1 hour from now)
        if (vehicle.getExpectedReturnTime() == null) {
            vehicle.setExpectedReturnTime(LocalDateTime.now().plusHours(1));
        }

        // Calculate penalty
        double penaltyAmount = penaltyService.calculatePenalty(vehicle);

        // Save penalty if applicable
        if (penaltyAmount > 0) {
            PenaltyHistory penalty = new PenaltyHistory();
            penalty.setVehicle(vehicle);
            penalty.setPenaltyAmount(penaltyAmount);
            penalty.setTimestamp(LocalDateTime.now());
            penaltyHistoryRepo.save(penalty);
        }

        vehicle.setStatus(RentalStatus.RETURNED);
        vehicle.setCurrentParkingZone(parkingZoneOpt.get());
        vehicleRepository.save(vehicle);

        return ResponseEntity.ok(
                penaltyAmount > 0
                        ? "Ride ended with a penalty of $" + penaltyAmount
                        : "Ride ended successfully, no penalty applied."
        );
    }
    // 🔹 API to Get Only Rented Vehicles
    @GetMapping("/rented")
    public ResponseEntity<List<Vehicle>> getRentedVehicles() {
        List<Vehicle> rentedVehicles = vehicleRepository.findByStatus(RentalStatus.RENTED);

        if (rentedVehicles.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(rentedVehicles); // 200 OK with data
    }



    // 🔹 Set a Vehicle as UNDER_MAINTENANCE
    @PutMapping("/{vehicleId}/maintenance")
    public ResponseEntity<?> markVehicleUnderMaintenance(@PathVariable String vehicleId) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle not found");
        }

        Vehicle vehicle = vehicleOpt.get();
        vehicle.setStatus(RentalStatus.UNDER_MAINTENANCE);
        vehicleRepository.save(vehicle);
        return ResponseEntity.ok("Vehicle marked as UNDER MAINTENANCE");
    }


    // 🔹 Helper Method: Calculate Distance Between Two Coordinates (Haversine Formula)
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // Convert to meters
    }
}
