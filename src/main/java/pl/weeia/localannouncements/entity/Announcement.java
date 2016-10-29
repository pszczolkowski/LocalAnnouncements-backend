package pl.weeia.localannouncements.entity;

import static javax.persistence.EnumType.STRING;
import static pl.weeia.localannouncements.entity.AnnouncementStatus.ACTIVE;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Size(min = 5, max = 100)
    private String name;
    
    @Size(min = 1, max = 5000)
    private String description;
    
    @NotNull
    private double latitude;
    
    @NotNull
    private double longitude;

    @NotNull
    @Enumerated(STRING)
    private AnnouncementStatus status;

    private LocalDateTime startTime;

    private Duration duration;
    
    @NotNull
    @ManyToOne
    private User creator;

    public Announcement(String name, String description, double latitude, double longitude,
            LocalDateTime startTime, Duration duration, User creator) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = ACTIVE;
        this.startTime = startTime;
        this.duration = duration;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public AnnouncementStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public User getCreator() {
        return creator;
    }
    
}
