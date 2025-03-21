package com.example.EV_Rentals.Entity;

public enum RentalStatus {
    AVAILABLE,  // Vehicle is ready for rent
    RENTED,     // Vehicle is currently rented
    RETURNED,   // Vehicle has been returned but not yet verified
    UNDER_MAINTENANCE, // Vehicle is being repaired or serviced
    LOST        // Vehicle is reported missing
}

