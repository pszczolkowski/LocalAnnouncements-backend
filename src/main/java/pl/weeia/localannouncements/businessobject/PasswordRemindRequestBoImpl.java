package pl.weeia.localannouncements.businessobject;

import org.springframework.beans.factory.annotation.Autowired;
import pl.weeia.localannouncements.entity.PasswordRemindRequest;
import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.PasswordRemindRequestRepository;
import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.service.userPasswordEncoder.PasswordEncodingService;
import pl.weeia.localannouncements.sharedkernel.annotations.BussinesObject;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by marcin on 23.10.16.
 */
@BussinesObject
public class PasswordRemindRequestBoImpl implements PasswordRemindRequestBO {

    private final PasswordRemindRequestRepository passwordRemindRequestRepository;
    private final UserRepository userRepository;
    private final PasswordEncodingService passwordEncodingService;

    @Autowired
    public PasswordRemindRequestBoImpl(UserRepository userRepository,
                                       PasswordRemindRequestRepository passwordRemindRequestRepository,
                                       PasswordEncodingService passwordEncodingService) {
        this.userRepository = userRepository;
        this.passwordRemindRequestRepository = passwordRemindRequestRepository;
        this.passwordEncodingService = passwordEncodingService;
    }

    @Override
    public PasswordRemindRequest queuePasswordChange(String activationToken, long userId, String password) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("user with id <" + userId + "> does not exist");
        }

        deactivateAllExistingRemindersFor(user);
        
        PasswordRemindRequest passwordRemindRequest = new PasswordRemindRequest(user, activationToken, LocalDateTime.now(),
                passwordEncodingService.encode(password));

        passwordRemindRequestRepository.save(passwordRemindRequest);
        return passwordRemindRequest;
    }

    private void deactivateAllExistingRemindersFor(User user) {
        List<PasswordRemindRequest> userPasswordRemindRequests = passwordRemindRequestRepository.findAllByUserAndValidTrue(user);
        for (PasswordRemindRequest passwordRemindRequest : userPasswordRemindRequests) {
            passwordRemindRequest.deactivate();
        }
    }

    @Override
    public void deactivate(Long id) {
        PasswordRemindRequest passwordRemindRequest = passwordRemindRequestRepository.findOne(id);
        if (passwordRemindRequest == null) {
            throw new IllegalArgumentException("PasswordRemindRequest with id <" + id + "> does not exist");
        }
        
        passwordRemindRequest.deactivate();
        passwordRemindRequestRepository.save(passwordRemindRequest);
    }

}
