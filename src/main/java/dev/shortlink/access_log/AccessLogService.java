package dev.shortlink.access_log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.shortlink.link.Link;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;

    public AccessLogService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public void addAccessLog(Link link, HttpServletRequest request, HttpServletResponse response) {

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        AccessLog accessLog = new AccessLog();
        
        accessLog.setLink(link);
        accessLog.setIpAddress(ipAddress);
        accessLog.setUserAgent(userAgent);
        accessLog.setReferer(referer);

        accessLogRepository.save(accessLog);
    }
}
