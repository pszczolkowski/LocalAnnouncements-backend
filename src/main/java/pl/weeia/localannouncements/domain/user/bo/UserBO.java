package pl.weeia.localannouncements.domain.user.bo;

import pl.weeia.localannouncements.domain.user.dto.UserSnapshot;

public interface UserBO {

   UserSnapshot register(String login, String password);

   void changePassword(Long id, String password);

}
