package pl.weeia.localannouncements.web.restapi.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import pl.weeia.localannouncements.entity.User;

@ApiModel
public class Account {

   private final Long id;
   private final String login;

   public Account(User user) {
      this.id = user.getId();
      this.login = user.getLogin();
   }

   @ApiModelProperty(value = "Account unique identifier")
   public Long getId() {
      return id;
   }

   @ApiModelProperty(value = "Username for account")
   public String getLogin() {
      return login;
   }

}
