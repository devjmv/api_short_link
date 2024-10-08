package dev.shortlink.link;

import dev.shortlink.access_log.AccessLogDTO;
import dev.shortlink.link_status.LinkStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkDTO {
    private Long id;
    private String originUrl;
    private String shortUrl;
    private Date expirationDate;
    private Long userId;
    private LinkStatusDTO status;
    private List<AccessLogDTO> AccessLogs;
}
