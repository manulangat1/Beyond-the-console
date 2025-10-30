package com.kipchirchirlangat.blog.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;
    @Column( nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category")
    private List<Post> posts = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(createdAt, category.createdAt) && Objects.equals(updatedAt, category.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, updatedAt);
    }

    @PrePersist
    protected  void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected  void  onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
