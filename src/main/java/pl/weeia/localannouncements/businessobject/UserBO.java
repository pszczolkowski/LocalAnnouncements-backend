package pl.weeia.localannouncements.businessobject;

import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.sharedkernel.constant.Gender;

public interface UserBO {

    User register(String login, String password, int age, Gender gender);
    
}
