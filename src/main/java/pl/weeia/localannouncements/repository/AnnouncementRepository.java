package pl.weeia.localannouncements.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.weeia.localannouncements.entity.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

}
