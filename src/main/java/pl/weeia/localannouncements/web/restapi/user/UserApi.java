package pl.weeia.localannouncements.web.restapi.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserApi {
    
	private UserRepository userRepository;
	
	@Autowired
	public UserApi(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<UserResponse>> list(){
		List<User> users = userRepository.findAll();
		List<UserResponse> usersResponse = users
				.stream()
				.map(UserResponse::new)
				.collect(Collectors.toList());
		
		return ResponseEntity
				.ok()
				.body(usersResponse);
	}
}
