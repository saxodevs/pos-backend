package com.saxodevs.pos.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String barcode;

    @Column(nullable = false)
    private Boolean isExpiryProduct;

    private LocalDate expiryDate;
    private LocalDate manufacturingDate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> images;

    @Column(nullable = false)
    private BigDecimal costPrice;

    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private BigDecimal markUp;

    @Column(nullable = false)
    private int reorderLevel;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "deleted_by_id")
    private User deletedBy;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        calculateSellingPrice();
        deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateSellingPrice();
    }

    protected void calculateSellingPrice() {

        BigDecimal hundred = new BigDecimal("100");


// sellingPrice = costPrice + (markUp * 100)


        this.sellingPrice = costPrice
                .multiply(
                        BigDecimal.ONE.add(markUp.divide(hundred))
                )
                .setScale(2, RoundingMode.HALF_UP);
    }

}
