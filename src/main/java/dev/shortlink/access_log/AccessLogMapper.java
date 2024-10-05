package dev.shortlink.access_log;

import org.springframework.stereotype.Component;

@Component
public class AccessLogMapper {

    // Method to convert AccessLog to AccessLogDTO
    public static AccessLogDTO toDTO(AccessLog accessLog) {
        if (accessLog == null) {
            return null;
        }

        return AccessLogDTO.builder()
                .id(accessLog.getId())
                .linkId(accessLog.getLink() != null ? accessLog.getLink().getId() : null) // Handle null link
                .accessTime(accessLog.getAccessTime())
                .ipAddress(accessLog.getIpAddress())
                .userAgent(accessLog.getUserAgent())
                .referer(accessLog.getReferer())
                .build();
    }

    // Method to convert AccessLogDTO to AccessLog
    public static AccessLog toEntity(AccessLogDTO dto) {
        if (dto == null) {
            return null;
        }

        AccessLog accessLog = new AccessLog();
        accessLog.setId(dto.getId());
        accessLog.setAccessTime(dto.getAccessTime());
        accessLog.setIpAddress(dto.getIpAddress());
        accessLog.setUserAgent(dto.getUserAgent());
        accessLog.setReferer(dto.getReferer());
        
        // You can set the link from the DTO if you have a Link object available
        // accessLog.setLink(new Link(dto.getLinkId()));

        return accessLog;
    }
}
