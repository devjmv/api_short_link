package dev.shortlink.link_status;

import dev.shortlink.link.Link;
import dev.shortlink.user.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "link_status")
public class LinkStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "status_updated_at", nullable = false, updatable = false)
    private Date statusUpdatedAt;

    @OneToOne
    @JoinColumn(name = "link_id", nullable = false)
    private Link link;

    @PrePersist
    protected void onUpdate() {
        this.statusUpdatedAt = new Date();
    }
}