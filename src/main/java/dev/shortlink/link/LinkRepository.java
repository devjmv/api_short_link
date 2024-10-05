package dev.shortlink.link;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.shortlink.user.User;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortUrl(String shortUrl);

    boolean existsByOriginUrl(String originUrl);

    boolean existsByShortUrl(String shortUrl);

    List<Link> findByUser(User user);

    @Query("SELECT COUNT(l) > 0 FROM Link l WHERE l.originUrl = :originUrl AND l.id != :linkId")
    boolean existsByOriginUrlAndNotId(@Param("originUrl") String originUrl, @Param("linkId") Long linkId);

    @Query("SELECT COUNT(l) > 0 FROM Link l WHERE l.shortUrl = :shortUrl AND l.id != :linkId")
    boolean existsByShortUrlAndNotId(@Param("shortUrl") String shortUrl, @Param("linkId") Long linkId);
}