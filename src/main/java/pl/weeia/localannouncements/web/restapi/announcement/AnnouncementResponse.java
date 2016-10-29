package pl.weeia.localannouncements.web.restapi.announcement;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.weeia.localannouncements.entity.Announcement;
import pl.weeia.localannouncements.entity.AnnouncementStatus;

@ApiModel
public class AnnouncementResponse {

    private final long id;
    private final String name;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final AnnouncementStatus status;
    private final LocalDateTime startTime;
    private final Long duration;
    
    public AnnouncementResponse(Announcement announcement) {
        this.id = announcement.getId();
        this.name = announcement.getName();
        this.description = announcement.getDescription();
        this.latitude = announcement.getLatitude();
        this.longitude = announcement.getLongitude();
        this.status = announcement.getStatus();
        this.startTime = announcement.getStartTime();
        this.duration = announcement.getDuration() == null ? null : announcement.getDuration().getSeconds();
    }

    @ApiModelProperty("Announcement identifier")
    public long getId() {
        return id;
    }

    @ApiModelProperty("Announcement name")
    public String getName() {
        return name;
    }

    @ApiModelProperty("Announcement description")
    public String getDescription() {
        return description;
    }

    @ApiModelProperty("Announcement latitude")
    public double getLatitude() {
        return latitude;
    }

    @ApiModelProperty("Announcement longitude")
    public double getLongitude() {
        return longitude;
    }

    @ApiModelProperty("Announcement status")
    public AnnouncementStatus getStatus() {
        return status;
    }

    @ApiModelProperty("Announcement start date")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @ApiModelProperty("Announcement duration in seconds")
    public Long getDuration() {
        return duration;
    }
    
}
