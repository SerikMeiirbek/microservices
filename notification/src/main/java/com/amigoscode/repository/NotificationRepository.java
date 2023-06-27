package com.amigoscode.repository;

import com.amigoscode.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer>{
}
