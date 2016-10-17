package pl.weeia.localannouncements.web.restapi.account;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import pl.weeia.localannouncements.businessobject.UserBO;
import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.service.userPasswordEncoder.PasswordEncodingService;

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final UserRepository userRepository;
    private final UserBO userBO;
    private final Validator userRegisterValidator;
    private final PasswordEncodingService passwordEncodingService;

    @Autowired
    public AccountApi(UserRepository userSnapshotFinder, UserBO userBO,
            @Qualifier("accountRegisterValidator") Validator userRegisterValidator, PasswordEncodingService passwordEncodingService) {
        this.userRepository = userSnapshotFinder;
        this.userBO = userBO;
        this.userRegisterValidator = userRegisterValidator;
        this.passwordEncodingService = passwordEncodingService;
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
        User userSnapshot = userBO.register(accountNew.getLogin(), accountNew.getPassword(), accountNew.getAge(),
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
    @RequestMapping(value = "/password", method = PUT)
    public HttpEntity<Account> changePassword(@Valid @RequestBody PasswordChange passwordChange) {
        User user = getLoggedUser();
        boolean isPreviousPasswordCorrect = passwordEncodingService.isMatch(passwordChange.getPreviousPassword(), user.getPassword());
        
        if (!isPreviousPasswordCorrect) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        userBO.setPassword(user.getId(), passwordChange.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
