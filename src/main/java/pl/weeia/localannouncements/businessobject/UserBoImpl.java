package pl.weeia.localannouncements.businessobject;

import org.springframework.beans.factory.annotation.Autowired;

import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.UserRepository;
import pl.weeia.localannouncements.service.userPasswordEncoder.PasswordEncodingService;
import pl.weeia.localannouncements.sharedkernel.annotations.BussinesObject;
import pl.weeia.localannouncements.sharedkernel.constant.Gender;

@BussinesObject
public class UserBoImpl implements UserBO {

    private final UserRepository userRepository;

    private final PasswordEncodingService passwordEncodingService;

    @Autowired
    public UserBoImpl(UserRepository userRepository, PasswordEncodingService passwordEncodingService) {
        this.userRepository = userRepository;
        this.passwordEncodingService = passwordEncodingService;
    }

    @Override
    public User register(String login, String password, int age, Gender gender, String email) {
        User user = new User(login, passwordEncodingService.encode(password), age, gender, email);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public void setPassword(long userId, String password) {
        User user = userRepository.findOne(userId);
        user.setPassword(passwordEncodingService.encode(password));
    }

    @Override
    public void setPasswordEncoded(long userId, String passwordHash) {
        User user = userRepository.findOne(userId);
        user.setPassword(passwordHash);
    }

}
