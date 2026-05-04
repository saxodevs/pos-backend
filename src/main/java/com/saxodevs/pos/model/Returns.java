package com.saxodevs.pos.model;

import com.saxodevs.pos.domain.ReturnStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Returns {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(nullable = false, unique = true)
    private String sku;

    @NotBlank
    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReturnStatus status;

    @OneToMany(mappedBy = "returns", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReturnItem> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @ManyToOne
    @JoinColumn(name = "rejected_by")
    private  User rejectedBy;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}