package dev.shortlink.link;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortUrl(String shortUrl);

    boolean existsByOriginUrl(String originUrl);

}