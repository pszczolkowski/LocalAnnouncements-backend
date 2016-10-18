package pl.weeia.localannouncements.web.restapi.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.sharedkernel.constant.Gender;

@ApiModel
public class Account {

    private final Long id;
    private final String username;
    private final int age;
    private final Gender gender;
    private final String email;

    public Account(User user) {
        this.id = user.getId();
        this.username = user.getLogin();
        this.age = user.getAge();
        this.gender = user.getGender();
        this.email = user.getEmail();
    }

    @ApiModelProperty(value = "Account unique identifier")
    public Long getId() {
        return id;
    }

    @ApiModelProperty(value = "Username for account")
    public String getUsername() {
        return username;
    }

    @ApiModelProperty(value = "User age")
    public int getAge() {
        return age;
    }

    @ApiModelProperty(value = "User gender")
    public Gender getGender() {
        return gender;
    }

    @ApiModelProperty(value = "User email")
    public String getEmail() {
        return email;
    }

}
