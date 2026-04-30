package com.saxodevs.pos.model;

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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String des;

    @Column(unique = true, nullable = false)
    private String sku;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}