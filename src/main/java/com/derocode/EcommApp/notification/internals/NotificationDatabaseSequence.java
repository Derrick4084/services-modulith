package com.derocode.EcommApp.notification.internals;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notification_sequences")
public class NotificationDatabaseSequence {
    @Id
    private String id;

    private long seq;
}
