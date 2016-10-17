package pl.weeia.localannouncements.web.restapi.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModelProperty;
import pl.weeia.localannouncements.sharedkernel.annotations.Email;
import pl.weeia.localannouncements.sharedkernel.constant.Gender;

public class AccountRegister {

	@NotNull
	@Size(min = 3, max = 20)
	@Pattern(regexp = "^[a-z0-9]*$")
	private String login;

	@NotNull
	@Size(min = 5, max = 30)
	private String password;
	
	@NotNull
	@Range(min = 1, max = 99)
	private int age;
	
	@NotNull
	private Gender gender;
	
	@NotEmpty
    @Size(max = 255)
	@Email
    private String email;

	@ApiModelProperty(value = "Username for new account")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@ApiModelProperty(value = "Password for new account")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ApiModelProperty(value = "User age for new account")
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@ApiModelProperty(value = "User gender for new account")
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@ApiModelProperty(value = "User email for new account")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
	
}
