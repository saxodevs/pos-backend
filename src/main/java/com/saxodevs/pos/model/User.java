package com.saxodevs.pos.model;

import com.saxodevs.pos.domain.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private UserRole role;

    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    @Column(name = "is_deleted")
    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "deleted_by_id")
    private User deletedBy;

    @OneToMany(mappedBy = "user")
    private List<Sale> sales;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
        deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
