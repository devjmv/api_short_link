package dev.shortlink.link_status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkStatusDTO {
    private Long id;
    private String status;
    private String reason;
    private Date statusUpdatedAt;
}
