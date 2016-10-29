package pl.weeia.localannouncements.web.restapi.announcement;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AnnouncementNew {

    @NotNull
    @Size(min = 5, max = 100)
    private String name;
    
    @Size(min = 1, max = 5000)
    private String description;
    
    @NotNull
    private double latitude;
    
    @NotNull
    private double longitude;

    private LocalDateTime startTime;

    private Integer duration;

    @ApiModelProperty(value = "Name for new announcement")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(value = "Description for new announcement")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ApiModelProperty(value = "Latitude for new announcement")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @ApiModelProperty(value = "Longitude for new announcement")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @ApiModelProperty(value = "Start date for new announcement")
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @ApiModelProperty(value = "Duration in seconds for new announcement")
    public Duration getDuration() {
        return duration == null ? null : Duration.ofSeconds(duration);
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
}
