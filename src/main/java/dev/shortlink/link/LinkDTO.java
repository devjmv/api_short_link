package dev.shortlink.link;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkDTO {
    private Long id;
    private String originUrl;
    private String shortUrl;
    private int clicks;
    private String expirationDate;
    private Long userId;
}
