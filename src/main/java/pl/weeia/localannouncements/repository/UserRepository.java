package pl.weeia.localannouncements.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.weeia.localannouncements.entity.User;

public interface UserRepository
   extends JpaRepository<User, Long> {

   User findOneByLoginIgnoreCase(String login);

}
