package pl.weeia.localannouncements.web.restapi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.sharedkernel.annotations.RestValidator;
import pl.weeia.localannouncements.web.restapi.commonvalidation.AbstractValidator;

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
	}

	private boolean loginIsTaken(String login) {
		return userRepository.findOneByLoginIgnoreCase(login) != null;
	}
}
