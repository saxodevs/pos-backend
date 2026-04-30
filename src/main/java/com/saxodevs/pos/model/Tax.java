package com.saxodevs.pos.model;


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
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String taxCode;

    @Column(nullable = false, unique = true)
    private String taxName;

    @Column(nullable = false)
    private Double taxRate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;


    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "deleted_by_id")
    private User deletedBy;



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
