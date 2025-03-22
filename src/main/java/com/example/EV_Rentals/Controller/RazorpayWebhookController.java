package com.example.EV_Rentals.Controller;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class RazorpayWebhookController {

    @PostMapping("/payment-success")
    public ResponseEntity<?> handlePaymentSuccess(@RequestBody String payload) {
        JSONObject event = new JSONObject(payload);
        String paymentId = event.getJSONObject("payload").getJSONObject("payment").getString("id");

        // 🔹 Store payment details in DB (You can implement PaymentHistory entity)
        System.out.println("Payment Successful! Payment ID: " + paymentId);

        return ResponseEntity.ok("Payment received successfully");
    }
}
