package pl.weeia.localannouncements.service.account;

public interface PasswordRemindingService {

    void setPasswordFromRemindRequest(String token);
    
}
