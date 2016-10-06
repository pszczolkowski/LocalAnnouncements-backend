package pl.weeia.localannouncements.domain.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.weeia.localannouncements.domain.user.entity.User;

public interface UserRepository
   extends JpaRepository<User, Long> {

   User findOneByLoginIgnoreCase(String login);

}
