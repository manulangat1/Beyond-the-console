package com.kipchirchirlangat.blog.services;

import com.kipchirchirlangat.blog.domain.CreatePostRequest;
import com.kipchirchirlangat.blog.domain.UpdatePostRequest;
import com.kipchirchirlangat.blog.domain.entities.Post;
import com.kipchirchirlangat.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId);


    List<Post> getPostWithDraftStatus(User user);

    Post createPost(User user
            , CreatePostRequest createPostRequest);

    Post updatePost(
            UUID id,
            UpdatePostRequest updatePostRequest
    );
}
