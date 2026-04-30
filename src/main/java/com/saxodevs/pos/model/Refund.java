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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private  Sale sale;

    private String reason;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "shift_report_id")
    private ShiftReport shiftReport;

    @ManyToOne
    @JoinColumn(name = "cashier_id")
    private User cashier;

    private PaymentMethod paymentMethod;

    private LocalDateTime createdAt;

    @PrePersist
    protected  void onCreate(){
        createdAt = LocalDateTime.now();
    }
}
