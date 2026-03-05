package com.habit.management.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@EqualsAndHashCode(exclude = {"roles", "habits"})
@ToString(exclude = {"roles", "habits"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "current_streak")
    private int currentStreak = 0;

    @Column(name = "max_streak")
    private int maxStreak = 0;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_role",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<HabitEntity> habits;

    @PrePersist
    private void prePersis() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
