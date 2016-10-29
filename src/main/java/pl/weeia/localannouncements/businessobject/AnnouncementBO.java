package pl.weeia.localannouncements.businessobject;

import java.time.Duration;
import java.time.LocalDateTime;

import pl.weeia.localannouncements.entity.Announcement;

public interface AnnouncementBO {

    Announcement create(String name, String description, double latitude, double longitude,
            LocalDateTime startTime, Duration duration, long creatorId);
    
}
