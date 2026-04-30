package com.saxodevs.pos.model;

import com.saxodevs.pos.domain.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    private String ref;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "deleted_by_id")
    private User deletedBy;


    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        deleted = false;
    }
}
