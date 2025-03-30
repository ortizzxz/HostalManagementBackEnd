package com.hostalmanagement.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // For REST API-triggered updates
    @PostMapping("/sendUpdate")
    public void sendUpdateViaRest(String message) {
        template.convertAndSend("/topic/updates", message);
    }

    // For STOMP message-triggered updates
    @MessageMapping("/update") // Clients send to /app/update
    @SendTo("/topic/updates")   // Broadcast to all subscribers
    public String handleStompUpdate(String message) {
        return message; // Process message here if needed
    }
}
