package com.example.EV_Rentals.Config;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class RideTrackingHandler extends TextWebSocketHandler {
    private static Map<String, WebSocketSession> adminSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        adminSessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Broadcast user location to admin panel
        for (WebSocketSession adminSession : adminSessions.values()) {
            adminSession.sendMessage(message);
        }
    }
}

