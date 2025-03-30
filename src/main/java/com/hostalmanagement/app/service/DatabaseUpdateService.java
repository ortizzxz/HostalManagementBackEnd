package com.hostalmanagement.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hostalmanagement.app.DTO.AnouncementDTO;

import java.util.List;

@Service
public class DatabaseUpdateService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private AnouncementService anouncementsService; // Replace with your actual repository

    private long lastProcessedId = 0; // Track the last processed record ID

    @Scheduled(fixedRate = 5000) // Check every 5 seconds
    public void checkForUpdates() {
        // Fetch new entries from the database (e.g., entries with IDs greater than lastProcessedId)
        List<AnouncementDTO> newEntries = anouncementsService.findByIdGreaterThan(lastProcessedId);

        for (AnouncementDTO entry : newEntries) {
            template.convertAndSend("/topic/updates", entry); // Broadcast each new entry
            lastProcessedId = Math.max(lastProcessedId, entry.getId()); // Update lastProcessedId
        }
    }
}
