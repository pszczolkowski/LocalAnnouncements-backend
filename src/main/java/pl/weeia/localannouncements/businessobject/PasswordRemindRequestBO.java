package pl.weeia.localannouncements.businessobject;

import pl.weeia.localannouncements.entity.PasswordRemindRequest;
import pl.weeia.localannouncements.entity.User;

import java.util.Date;

/**
 * Created by marcin on 23.10.16.
 */
public interface PasswordRemindRequestBO {

    PasswordRemindRequest queuePasswordChange(User user, String activationToken, Date requested, String password);
    void deactivate(Long id);
}
