package dev.shortlink.access_log;

import dev.shortlink.link.Link;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "access_log")
public class AccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id", nullable = false)
    private Link link;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "access_time", nullable = false)
    private Date accessTime;

    private String ipAddress;
    private String userAgent;
    private String referer;

    @PrePersist
    protected void onCreate() {
        this.accessTime = new Date();
    }
}
