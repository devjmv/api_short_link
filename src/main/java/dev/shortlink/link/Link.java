package dev.shortlink.link;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

import dev.shortlink.accessLog.AccessLog;
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

    private int clicks;

    @Column(name = "expiration_date")
    private String expirationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "access_log", cascade = CascadeType.ALL)
    private List<AccessLog> accessLogs;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        this.clicks = 0;
    }
}
