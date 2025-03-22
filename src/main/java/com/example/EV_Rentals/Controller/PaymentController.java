package com.example.EV_Rentals.Controller;

import com.example.EV_Rentals.Entity.Vehicle;
import com.example.EV_Rentals.Repository.VehicleRepo;
import com.example.EV_Rentals.Service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final VehicleRepo vehicleRepo;

    public PaymentController(PaymentService paymentService, VehicleRepo vehicleRepo) {
        this.paymentService = paymentService;
        this.vehicleRepo = vehicleRepo;
    }

    // 🔹 Initiate Payment for a Ride
    @PostMapping("/pay/{vehicleId}")
    public ResponseEntity<?> initiatePayment(@PathVariable String vehicleId, @RequestParam String userEmail) {
        Optional<Vehicle> vehicleOpt = vehicleRepo.findById(vehicleId);

        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Vehicle not found!");
        }

        Vehicle vehicle = vehicleOpt.get();
        if (vehicle.getRideStartTime() == null || vehicle.getExpectedReturnTime() == null) {
            return ResponseEntity.badRequest().body("Ride times not set!");
        }

        try {
            String orderResponse = paymentService.createPaymentOrder(vehicle.getRideStartTime(), vehicle.getExpectedReturnTime(), userEmail);
            return ResponseEntity.ok(orderResponse);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating payment order: " + e.getMessage());
        }
    }
}
