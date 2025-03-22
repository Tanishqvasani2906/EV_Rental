package com.example.EV_Rentals.Controller;

import com.example.EV_Rentals.Entity.ParkingZone;
import com.example.EV_Rentals.Entity.Vehicle;
import com.example.EV_Rentals.Repository.ParkingZoneRepo;
import com.example.EV_Rentals.Repository.VehicleRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking-zones")
public class ParkingZoneController {

    private final ParkingZoneRepo parkingZoneRepo;
    private final VehicleRepo vehicleRepo;

    public ParkingZoneController(ParkingZoneRepo parkingZoneRepo, VehicleRepo vehicleRepo) {
        this.parkingZoneRepo = parkingZoneRepo;
        this.vehicleRepo = vehicleRepo;
    }

    // 🔹 1. Add a new Parking Zone
    @PostMapping("/add")
    public ResponseEntity<?> addParkingZone(@RequestBody ParkingZone parkingZone) {
        if (parkingZoneRepo.findByParkingName(parkingZone.getParkingName()) != null) {
            return ResponseEntity.badRequest().body("Parking Zone with this name already exists!");
        }
        ParkingZone savedZone = parkingZoneRepo.save(parkingZone);
        return ResponseEntity.ok(savedZone);
    }

    // 🔹 2. Get all Parking Zones
    @GetMapping("/getAllPZ")
    public ResponseEntity<List<ParkingZone>> getAllParkingZones() {
        return ResponseEntity.ok(parkingZoneRepo.findAll());
    }

    // 🔹 3. Get Parking Zone by ID
    @GetMapping("/getPZById/{parkingZoneId}")
    public ResponseEntity<?> getParkingZoneById(@PathVariable String parkingZoneId) {
        Optional<ParkingZone> parkingZone = parkingZoneRepo.findById(parkingZoneId);

        if (parkingZone.isPresent()) {
            return ResponseEntity.ok(parkingZone.get()); // ✅ Correctly returns ParkingZone
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // ✅ Explicitly set HTTP status
                    .body(Collections.singletonMap("error", "Parking Zone not found!")); // ✅ JSON response
        }
    }


    // 🔹 4. Update Parking Zone details
    @PutMapping("/updatePZById/{parkingZoneId}")
    public ResponseEntity<?> updateParkingZone(@PathVariable String parkingZoneId, @RequestBody ParkingZone updatedZone) {
        Optional<ParkingZone> existingZone = parkingZoneRepo.findById(parkingZoneId);

        if (existingZone.isEmpty()) {
            return ResponseEntity.badRequest().body("Parking Zone not found!");
        }

        ParkingZone zone = existingZone.get();
        zone.setParkingName(updatedZone.getParkingName());
        zone.setCenterLatitude(updatedZone.getCenterLatitude());
        zone.setCenterLongitude(updatedZone.getCenterLongitude());
        zone.setRadius(updatedZone.getRadius());
        zone.setCapacity(updatedZone.getCapacity());

        parkingZoneRepo.save(zone);
        return ResponseEntity.ok("Parking Zone updated successfully!");
    }

    // 🔹 5. Delete a Parking Zone
    @DeleteMapping("/deletePZById/{parkingZoneId}")
    public ResponseEntity<?> deleteParkingZone(@PathVariable String parkingZoneId) {
        Optional<ParkingZone> existingZone = parkingZoneRepo.findById(parkingZoneId);

        if (existingZone.isEmpty()) {
            return ResponseEntity.badRequest().body("Parking Zone not found!");
        }

        parkingZoneRepo.deleteById(parkingZoneId);
        return ResponseEntity.ok("Parking Zone deleted successfully!");
    }

    // 🔹 6. Get Available Parking Slots
    @GetMapping("/{parkingZoneId}/availability")
    public ResponseEntity<?> getParkingAvailability(@PathVariable String parkingZoneId) {
        Optional<ParkingZone> parkingZoneOpt = parkingZoneRepo.findById(parkingZoneId);
        if (parkingZoneOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Parking Zone not found!");
        }

        ParkingZone parkingZone = parkingZoneOpt.get();
        long parkedVehicles = vehicleRepo.countByCurrentParkingZone(parkingZone);
        int availableSlots = parkingZone.getCapacity() - (int) parkedVehicles;

        return ResponseEntity.ok(
                String.format("Available slots: %d out of %d", availableSlots, parkingZone.getCapacity())
        );
    }
    // 🔹 7. Get all Vehicles by Parking Zone ID
    @GetMapping("/getVehiclesByPZId/{parkingZoneId}")
    public ResponseEntity<?> getVehiclesByParkingZone(@PathVariable String parkingZoneId) {
        Optional<ParkingZone> parkingZoneOpt = parkingZoneRepo.findById(parkingZoneId);

        if (parkingZoneOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Parking Zone not found!");
        }

        List<Vehicle> vehicles = vehicleRepo.findByParkingZoneId(parkingZoneId);

        if (vehicles.isEmpty()) {
            return ResponseEntity.ok("No vehicles found in this parking zone.");
        }

        return ResponseEntity.ok(vehicles);
    }

}

