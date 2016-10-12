package pl.weeia.localannouncements.entity;

import static javax.persistence.EnumType.STRING;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import pl.weeia.localannouncements.sharedkernel.constant.Gender;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -7910624447570146282L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
    @Size(min = 3, max = 20)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(length = 60)
    private String password;

    @NotNull
    @Range(min = 1, max = 99)
    private int age;

    @NotNull
    @Enumerated(STRING)
    private Gender gender;

    protected User() {
    }

    public User(String login, String password, int age, Gender gender) {
	this.login = login;
	this.password = password;
	this.age = age;
	this.gender = gender;
    }

    public void changePassword(String password) {
	this.password = password;
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (o == null || getClass() != o.getClass()) {
	    return false;
	}

	User user = (User) o;

	if (!login.equals(user.login)) {
	    return false;
	}

	return true;
    }

    @Override
    public int hashCode() {
	return login.hashCode();
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }
    
}
