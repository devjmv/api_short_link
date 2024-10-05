package dev.shortlink.link;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortUrl(Long shortUrl);

}