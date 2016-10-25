package pl.weeia.localannouncements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.weeia.localannouncements.entity.PasswordRemindRequest;
import pl.weeia.localannouncements.entity.User;

/**
 * Created by marcin on 23.10.16.
 */
public interface PasswordRemindRequestRepository extends JpaRepository<PasswordRemindRequest, Long>
{

        PasswordRemindRequest findOneByActivationToken(String activationToken);
        PasswordRemindRequest findOneByUser(User user);

}
