package pl.weeia.localannouncements.web.restapi.account;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.weeia.localannouncements.businessobject.PasswordRemindRequestBO;
import pl.weeia.localannouncements.businessobject.UserBO;
import pl.weeia.localannouncements.entity.PasswordRemindRequest;
import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.PasswordRemindRequestRepository;
import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.service.account.PasswordRemindingService;
import pl.weeia.localannouncements.service.userPasswordEncoder.PasswordEncodingService;
import pl.weeia.localannouncements.sharedkernel.util.MailSender;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final UserRepository userRepository;
    private final PasswordRemindRequestBO passwordRemindRequestBO;
    private final UserBO userBO;
    private final Validator userRegisterValidator;
    private final PasswordEncodingService passwordEncodingService;
    private final PasswordRemindRequestRepository passwordRemindRequestRepository;
    private final PasswordRemindingService passwordRemindingService;

    @Autowired
    public AccountApi(UserRepository userSnapshotFinder, UserBO userBO, PasswordRemindRequestBO passwordRemindRequestBO,
            @Qualifier("accountRegisterValidator") Validator userRegisterValidator, PasswordEncodingService passwordEncodingService,
            PasswordRemindRequestRepository passwordRemindRequestRepository, PasswordRemindingService passwordRemindingService) {
        this.userRepository = userSnapshotFinder;
        this.userBO = userBO;
        this.userRegisterValidator = userRegisterValidator;
        this.passwordEncodingService = passwordEncodingService;
        this.passwordRemindRequestRepository = passwordRemindRequestRepository;
        this.passwordRemindRequestBO = passwordRemindRequestBO;
        this.passwordRemindingService = passwordRemindingService;
    }

    @InitBinder("accountRegister")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(userRegisterValidator);
    }

    private User getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findOneByLoginIgnoreCase(username);
    }

    @ApiOperation(value = "Create new account")
    @ApiResponses({ @ApiResponse(code = 200, message = "Account created") })
    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Account> register(@Valid @RequestBody AccountRegister accountNew) {
        User userSnapshot = userBO.register(accountNew.getLogin(), accountNew.getRawPassword(), accountNew.getAge(),
                accountNew.getGender(), accountNew.getEmail());

        return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
    }

    @ApiOperation(value = "Get account of logged user", notes = "Returns account of logged user")
    @ApiResponses({ @ApiResponse(code = 200, message = "Found account of logged user") })
    @RequestMapping(method = GET)
    @PreAuthorize("isAuthenticated()")
    public HttpEntity<Account> get() {
        User user = getLoggedUser();
        return new ResponseEntity<>(new Account(user), HttpStatus.OK);
    }

    @ApiOperation("Change password for account with id")
    @ApiResponses({ @ApiResponse(code = 200, message = "Changed password for account") })
    @RequestMapping(value = "password", method = PUT)
    public HttpEntity<Account> changePassword(@Valid @RequestBody PasswordChange passwordChange) {
        User user = getLoggedUser();
        boolean isPreviousPasswordCorrect = passwordEncodingService.isMatch(passwordChange.getPreviousPassword(), user.getPassword());
        
        if (!isPreviousPasswordCorrect) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        userBO.setPassword(user.getId(), passwordChange.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("Remind password for account with given username and email")
    @ApiResponses({ @ApiResponse(code = 200, message = "Password changing link sent on email address") })
    @RequestMapping(value = "password/remind", method = POST)
    public HttpEntity<?> remindPassword(@Valid @RequestBody PasswordRemind passwordRemind)
    {
        User user = userRepository.findOneByEmailIgnoreCase(passwordRemind.getEmail());
        if(user != null && user.getLogin().equals(passwordRemind.getLogin()))
        {
            SecureRandom random = new SecureRandom();
            String token = new BigInteger(130, random).toString(32);
            String rawPassword = RandomStringUtils.randomAlphabetic(8);
            passwordRemindRequestBO.queuePasswordChange(user,token,new Date(), rawPassword);
            MailSender.sendMail(user.getEmail(), "Password change",
                    "We have generated new password for you account: " + rawPassword + ". In order to activate it, click this link: account/password_reminder/activate?token=" + token);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @ApiOperation("Activate password change for account with given token")
    @ApiResponses({ @ApiResponse(code = 200, message = "Changed generated password for account") })
    @RequestMapping(value = "password_reminder/activate")
    public HttpEntity<?> activatePassword(@RequestParam(value = "token", required = true)  String token) {
        PasswordRemindRequest passwordRemindRequest =
                passwordRemindRequestRepository.findOneByActivationToken(token);
        if(passwordRemindRequest != null && passwordRemindRequest.isValid())
        {
            passwordRemindingService.setPasswordFromRemindRequest(token);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
