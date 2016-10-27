package pl.weeia.localannouncements.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.weeia.localannouncements.businessobject.PasswordRemindRequestBO;
import pl.weeia.localannouncements.businessobject.UserBO;
import pl.weeia.localannouncements.entity.PasswordRemindRequest;
import pl.weeia.localannouncements.repository.PasswordRemindRequestRepository;

@Service
@Transactional
public class PasswordRemindingServiceImpl implements PasswordRemindingService {

    private final UserBO userBO;
    private final PasswordRemindRequestRepository passwordRemindRequestRepository;
    private final PasswordRemindRequestBO passwordRemindRequestBO;

    @Autowired
    public PasswordRemindingServiceImpl(UserBO userBO,
            PasswordRemindRequestRepository passwordRemindRequestRepository, PasswordRemindRequestBO passwordRemindRequestBO) {
        this.userBO = userBO;
        this.passwordRemindRequestRepository = passwordRemindRequestRepository;
        this.passwordRemindRequestBO = passwordRemindRequestBO;
    }

    @Override
    public void setPasswordFromRemindRequest(String token) {
        PasswordRemindRequest passwordRemindRequest = passwordRemindRequestRepository.findOneByActivationToken(token);
        if (passwordRemindRequest == null) {
            throw new IllegalArgumentException("PasswordRemindRequest with given token <" + token + "> does not exist");
        } else if (!passwordRemindRequest.isValid()) {
            throw new IllegalArgumentException("PasswordRemindRequest with given token <" + token + "> is not active");
        }
        
        userBO.setPasswordEncoded(passwordRemindRequest.getUser().getId(), passwordRemindRequest.getPassword());
        passwordRemindRequestBO.deactivate(passwordRemindRequest.getId());        
    }

}
