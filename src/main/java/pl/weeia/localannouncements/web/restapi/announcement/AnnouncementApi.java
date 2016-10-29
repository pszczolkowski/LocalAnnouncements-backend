package pl.weeia.localannouncements.web.restapi.announcement;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.weeia.localannouncements.businessobject.AnnouncementBO;
import pl.weeia.localannouncements.entity.Announcement;
import pl.weeia.localannouncements.repository.UserRepository;

@RestController
@RequestMapping("/announcement")
public class AnnouncementApi {

    private final AnnouncementBO announcementBO;
    private final UserRepository userRepository;

    @Autowired
    public AnnouncementApi(AnnouncementBO announcementBO, UserRepository userRepository) {
        this.announcementBO = announcementBO;
        this.userRepository = userRepository;
    }
    
    @ApiOperation(value = "Create new announcement")
    @ApiResponses({ @ApiResponse(code = 200, message = "Announcement created") })
    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public HttpEntity<AnnouncementResponse> create(@Valid @RequestBody AnnouncementNew announcementNew) {
        Announcement announcement = announcementBO.create(announcementNew.getName(), announcementNew.getDescription(),
                announcementNew.getLatitude(), announcementNew.getLongitude(), announcementNew.getStartTime(),
                announcementNew.getDuration(), getLoggedUserId());
        
        return ResponseEntity
                .status(CREATED)
                .body(new AnnouncementResponse(announcement));
    }
    
    private long getLoggedUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findOneByLoginIgnoreCase(username).getId();
    }
    
}
