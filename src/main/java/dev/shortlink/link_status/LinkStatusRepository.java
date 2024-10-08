package dev.shortlink.link_status;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LinkStatusRepository extends JpaRepository<LinkStatus, Long> {

    Optional<LinkStatus> findByLinkId(Long linkId);
}