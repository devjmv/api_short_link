package dev.shortlink.access_log;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessLogDTO {
    private Long id;
    private Long linkId;
    private Date accessTime;
    private String ipAddress;
    private String userAgent;
    private String referrer;
}
