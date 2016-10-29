package pl.weeia.localannouncements.businessobject;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import pl.weeia.localannouncements.entity.Announcement;
import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.AnnouncementRepository;
import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.sharedkernel.annotations.BussinesObject;

@BussinesObject
public class AnnouncementBOImpl implements AnnouncementBO {

    private final UserRepository userRepository;
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementBOImpl(UserRepository userRepository, AnnouncementRepository announcementRepository) {
        this.userRepository = userRepository;
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement create(String name, String description, double latitude, double longitude,
            LocalDateTime startTime, Duration duration, long creatorId) {
        User user = userRepository.findOne(creatorId);
        if (user == null) {
            throw new IllegalArgumentException("user with id <" + creatorId + "> does not exist");
        }
        
        Announcement announcement = new Announcement(name, description, latitude, longitude, startTime, duration, user);
        announcement = announcementRepository.saveAndFlush(announcement);
        
        return announcement;
    }


}
