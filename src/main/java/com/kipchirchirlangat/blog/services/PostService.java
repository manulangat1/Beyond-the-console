package com.kipchirchirlangat.blog.services;

import com.kipchirchirlangat.blog.domain.entities.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId);
}
