package com.derocode.EcommApp.notification.repository;


import com.derocode.EcommApp.notification.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, Long> {
}
