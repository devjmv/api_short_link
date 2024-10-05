package dev.shortlink.link;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

import dev.shortlink.access_log.AccessLog;
import dev.shortlink.link_status.LinkStatus;
import dev.shortlink.user.User;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;

    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "short_url", nullable = false)
    private String shortUrl;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "link")
    private List<AccessLog> accessLogs;

    @OneToOne(mappedBy = "link")
    private LinkStatus linkStatus;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
