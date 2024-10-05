package dev.shortlink.link;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.shortlink.user.User;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortUrl(String shortUrl);

    boolean existsByOriginUrl(String originUrl);

    boolean existsByShortUrl(String shortUrl);

    List<Link> findByUser(User user);

}