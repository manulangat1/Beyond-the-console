package com.kipchirchirlangat.blog.repositories;

import com.kipchirchirlangat.blog.domain.PostStatus;
import com.kipchirchirlangat.blog.domain.entities.Category;
import com.kipchirchirlangat.blog.domain.entities.Post;
import com.kipchirchirlangat.blog.domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagContaining(
            PostStatus status,
            Category category,
            Tag tag
    );

    List<Post> findAllByStatusAndCategory(
            PostStatus status, Category category
    );

    List<Post> findAllByStatusAndTag(
            PostStatus status, Tag tag
    );

    List<Post> findAllByStatus(
            PostStatus status
    );


}
