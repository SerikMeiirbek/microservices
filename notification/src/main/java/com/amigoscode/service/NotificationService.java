package com.amigoscode.service;

import com.amigoscode.clients.notification.NotificationRequest;
import com.amigoscode.model.Notification;
import com.amigoscode.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public void send(NotificationRequest notificationRequest) {

        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("Amigoscode")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );

    }
}
