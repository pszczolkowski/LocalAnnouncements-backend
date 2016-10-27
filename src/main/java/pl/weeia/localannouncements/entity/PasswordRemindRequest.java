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
    @OneToOne
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PasswordRemindRequest passwordRemindRequest = (PasswordRemindRequest) o;
        if (!passwordRemindRequest.getUser().getLogin().equals(user.getLogin())) {
            return false;
        }

        return true;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRequested(LocalDateTime requested) {
        this.requested = requested;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void deactivate() { valid = false;}

    public boolean isValid() { return  valid;}
}
