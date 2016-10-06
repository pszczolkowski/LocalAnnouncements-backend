package pl.weeia.localannouncements.web.restapi.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import pl.weeia.localannouncements.domain.user.finder.UserSnapshotFinder;
import pl.weeia.localannouncements.sharedkernel.annotations.RestValidator;
import pl.weeia.localannouncements.web.restapi.commonvalidation.AbstractValidator;

@RestValidator
public class AccountRegisterValidator extends AbstractValidator {

	private final UserSnapshotFinder userSnapshotFinder;

	@Autowired
	public AccountRegisterValidator(UserSnapshotFinder userSnapshotFinder) {
		this.userSnapshotFinder = userSnapshotFinder;
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
		return userSnapshotFinder.findByLogin(login) != null;
	}
}
