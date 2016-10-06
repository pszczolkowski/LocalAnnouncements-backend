package pl.weeia.localannouncements.service.userPasswordEncoder;

public interface PasswordEncodingService {

   String encode(String rawPassword);

   boolean isMatch(String passwordToCheck, String encodedPassword);

}
