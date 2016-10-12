package pl.weeia.localannouncements.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.weeia.localannouncements.entity.User;
import pl.weeia.localannouncements.repository.UserRepository;


@Component("userDetailsService")
public class UserDetailsService
   implements org.springframework.security.core.userdetails.UserDetailsService {

   private final UserRepository userRepository;

   @Autowired
   public UserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      User user = userRepository.findOneByLoginIgnoreCase(login);

      if (user == null) {
         throw new UsernameNotFoundException("User with login " + login + " was not found in database");
      }

      return new CustomUserDetails(user.getId(), user.getLogin(), user.getPassword());

   }
}
