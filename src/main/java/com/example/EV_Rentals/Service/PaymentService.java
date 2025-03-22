package com.example.EV_Rentals.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String razorpayKey;

    @Value("${razorpay.key_secret}")
    private String razorpaySecret;

    private RazorpayClient razorpayClient;

    // ✅ FIX: Initialize RazorpayClient in the constructor correctly
    public PaymentService(@Value("${razorpay.key_id}") String key, @Value("${razorpay.key_secret}") String secret) throws RazorpayException {
        this.razorpayClient = new RazorpayClient(key, secret);
    }

    // 🔹 Create Razorpay Payment Order
    public String createPaymentOrder(LocalDateTime startTime, LocalDateTime expectedReturnTime, String userEmail) throws RazorpayException {
        long rideDurationMinutes = Duration.between(startTime, expectedReturnTime).toMinutes();

        // 🔹 Define pricing (e.g., ₹2 per minute)
        int farePerMinute = 2;
        int totalAmount = (int) (rideDurationMinutes * farePerMinute * 100); // Convert to paise

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcpt_" + System.currentTimeMillis());
        orderRequest.put("payment_capture", 1);

        // ✅ FIX: Use the correct way to create an order
        Order order = razorpayClient.Orders.create(orderRequest);

        return order.toString(); // Return order details
    }
}
