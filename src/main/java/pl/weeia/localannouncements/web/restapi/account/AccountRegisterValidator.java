package pl.weeia.localannouncements.web.restapi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.sharedkernel.annotations.RestValidator;
import pl.weeia.localannouncements.web.restapi.commonvalidation.AbstractValidator;

import javax.validation.ParameterNameProvider;
import javax.validation.executable.ExecutableValidator;

@RestValidator
public class AccountRegisterValidator extends AbstractValidator {

	private final UserRepository userRepository;

	@Autowired
	public AccountRegisterValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return AccountRegister.class.isAssignableFrom(clazz);
	}

	@Override
	public void customValidation(Object target, Errors errors) {
		AccountRegister accountRegister = (AccountRegister) target;
		
		if (loginIsTaken(accountRegister.getLogin())) {
			errors.rejectValue("login", "LoginAlreadyInUse");
		}
		
		if (emailIsAlreadyTaken(accountRegister.getEmail())) {
		    errors.rejectValue("email", "EmailAlreadyInUse");
		}
	}

    private boolean loginIsTaken(String login) {
		return userRepository.findOneByLoginIgnoreCase(login) != null;
	}
    
    private boolean emailIsAlreadyTaken(String email) {
        return userRepository.findOneByEmailIgnoreCase(email) != null;
    }

    @Override
    public ExecutableValidator forExecutables() {
        return null;
    }

    @Override
    public ParameterNameProvider getParameterNameProvider() {
        return null;
    }
}
