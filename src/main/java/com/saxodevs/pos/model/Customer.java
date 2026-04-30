package com.saxodevs.pos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "deleted_by_id")
    private User deletedBy;

    @OneToMany(mappedBy = "customer")
    private List<Sale> sales;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private User updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt=LocalDateTime.now();
        deleted = false;
    }
    @PreUpdate
    protected  void onUpdate(){
        updatedAt = LocalDateTime.now();
    }
}
