package com.saxodevs.pos.model;

import com.saxodevs.pos.domain.ChangeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    private int qtyChange;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}