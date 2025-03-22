package com.example.EV_Rentals.Controller;

import com.example.EV_Rentals.Entity.PenaltyHistory;
import com.example.EV_Rentals.Repository.PenaltyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class PenaltyController {
    @Autowired
    private PenaltyRepo penaltyHistoryRepository;
    @GetMapping("/penalty-history/{vehicleId}")
    public ResponseEntity<List<PenaltyHistory>> getPenaltyHistory(@PathVariable String vehicleId) {
        List<PenaltyHistory> penalties = penaltyHistoryRepository.findPenaltyByVehicleId(vehicleId);
        return ResponseEntity.ok(penalties);
    }

}
