package dev.shortlink.access_log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.shortlink.link.Link;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    List<AccessLog> findAllByLink(Link link);
}