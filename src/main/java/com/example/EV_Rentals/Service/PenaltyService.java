package com.example.EV_Rentals.Service;

import com.example.EV_Rentals.Entity.Vehicle;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PenaltyService {

    public double calculatePenalty(Vehicle vehicle) {
        LocalDateTime expectedTime = vehicle.getExpectedReturnTime();
        LocalDateTime actualTime = vehicle.getActualReturnTime();

        // No penalty if returned on time
        if (actualTime.isBefore(expectedTime) || actualTime.isEqual(expectedTime)) {
            return 0.0;
        }

        // Calculate late duration in minutes
        long minutesLate = Duration.between(expectedTime, actualTime).toMinutes();

        // Apply penalties based on delay
        if (minutesLate <= 30) {
            return 20.00;  // Flat penalty for 10-30 mins delay
        } else if (minutesLate <= 60) {
            return 30.00; // Medium penalty
        } else {
            return 50.00; // High penalty (severe delay)
        }
    }
}
