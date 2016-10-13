package pl.weeia.localannouncements.web.restapi.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordChange {

    @NotNull
    @Size(min = 5, max = 30)
    private String previousPassword;
    
    @NotNull
    @Size(min = 5, max = 30)
    private String newPassword;

    public String getPreviousPassword() {
        return previousPassword;
    }

    public void setPreviousPassword(String previousPassword) {
        this.previousPassword = previousPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
