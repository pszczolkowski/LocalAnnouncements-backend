package pl.weeia.localannouncements.businessobject;

import pl.weeia.localannouncements.entity.PasswordRemindRequest;

/**
 * Created by marcin on 23.10.16.
 */
public interface PasswordRemindRequestBO {

    PasswordRemindRequest queuePasswordChange(String activationToken, long userId, String password);
    void deactivate(Long id);
}
