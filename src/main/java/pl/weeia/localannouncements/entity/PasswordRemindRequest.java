package pl.weeia.localannouncements.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Created by marcin on 23.10.16.
 */
@Entity
@Table(name = "passwd_remind_request")
public class PasswordRemindRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @NotNull
    @Column(columnDefinition="TIMESTAMP")
    private LocalDateTime requested;

    @NotEmpty
    @Size(max = 128)
    @Column(unique = true)
    private String activationToken;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private boolean valid = true;

    public PasswordRemindRequest(User user, String activationToken, LocalDateTime requested, String password) {
        this.user = user;
        this.activationToken = activationToken;
        this.requested = requested;
        this.password = password;
    }

    public PasswordRemindRequest() {    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getRequested() {
        return requested;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void deactivate() { valid = false;}

    public boolean isValid() { return  valid;}
}
