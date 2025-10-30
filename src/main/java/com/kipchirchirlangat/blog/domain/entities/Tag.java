package com.kipchirchirlangat.blog.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tag")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @Column( nullable = false, unique = true)
    private String name;


    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id) && Objects.equals(name, tag.name) && Objects.equals(createdAt, tag.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt);
    }

    @PrePersist
    protected  void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
