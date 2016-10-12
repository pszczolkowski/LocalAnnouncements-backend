package pl.weeia.localannouncements.web.restapi.user;

import pl.weeia.localannouncements.entity.User;

public class UserResponse {
	private Long id;
	private String login;
	
	public UserResponse(User user) {
		this.id = user.getId();
		this.login = user.getLogin();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getLogin() {
		return login;
	}
}
