package com.saxodevs.pos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"return_id", "product_id"})
)
public class ReturnItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "return_id", nullable = false)
    private Returns returns;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Min(1)
    @Column(nullable = false)
    private int quantity;
}
