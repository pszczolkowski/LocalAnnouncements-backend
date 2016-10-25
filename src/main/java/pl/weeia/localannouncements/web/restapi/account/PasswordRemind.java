package pl.weeia.localannouncements.web.restapi.account;

import org.hibernate.validator.constraints.NotEmpty;
import pl.weeia.localannouncements.sharedkernel.annotations.Email;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PasswordRemind {

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
    @Size(min = 3, max = 20)
    private String login;

    @NotEmpty
    @Size(max = 255)
    @Email
    private String email;


    public String getLogin() {
        return login;
    }

    public void setLogin(String newLogin) {
        this.login = newLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

}
