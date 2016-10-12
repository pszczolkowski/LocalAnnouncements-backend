package pl.weeia.localannouncements.web.restapi.account;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/account")
public class AccountApi {

    private final UserRepository userRepository;
    private final UserBO userBO;
    private final Validator userRegisterValidator;

    @Autowired
    public AccountApi(UserRepository userSnapshotFinder, UserBO userBO,
            @Qualifier("accountRegisterValidator") Validator userRegisterValidator) {
        this.userRepository = userSnapshotFinder;
        this.userBO = userBO;
        this.userRegisterValidator = userRegisterValidator;
    }

    @InitBinder("accountRegister")
    protected void initNewBinder(WebDataBinder binder) {
        binder.setValidator(userRegisterValidator);
    }

    private User getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findOneByLoginIgnoreCase(username);
    }

    @ApiOperation(value = "Create new account", notes = "Returns login and id of created account")
    @ApiResponses({ @ApiResponse(code = 200, message = "Account created") })
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public HttpEntity<Account> register(@Valid @RequestBody AccountRegister accountNew) {
        User userSnapshot = userBO.register(accountNew.getLogin(), accountNew.getPassword(), accountNew.getAge(),
                accountNew.getGender());

        return new ResponseEntity<>(new Account(userSnapshot), HttpStatus.OK);
    }

    @ApiOperation(value = "Get account of logged user", notes = "Returns account of logged user")
    @ApiResponses({ @ApiResponse(code = 200, message = "Found account of logged user") })
    @RequestMapping(method = GET)
    public HttpEntity<Account> get() {
        User user = getLoggedUser();
        return new ResponseEntity<>(new Account(user), HttpStatus.OK);
    }

    @ApiOperation(value = "Change password for account with id", notes = "Empty body")
    @ApiResponses({ @ApiResponse(code = 200, message = "Changed password for account") })
    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public HttpEntity<Account> changePassword(@RequestBody String password) {
        User user = getLoggedUser();

        // TODO
        // userBO.changePassword(user.getId(), password);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
