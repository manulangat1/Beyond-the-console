package com.kipchirchirlangat.blog.controllers;


import com.kipchirchirlangat.blog.domain.CreatePostRequest;
import com.kipchirchirlangat.blog.domain.UpdatePostRequest;
import com.kipchirchirlangat.blog.domain.dtos.CreatePostRequestDto;
import com.kipchirchirlangat.blog.domain.dtos.PostDto;
import com.kipchirchirlangat.blog.domain.dtos.UpdatePostRequestDTO;
import com.kipchirchirlangat.blog.domain.entities.Post;
import com.kipchirchirlangat.blog.domain.entities.User;
import com.kipchirchirlangat.blog.mappers.PostMapper;
import com.kipchirchirlangat.blog.services.PostService;
import com.kipchirchirlangat.blog.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@AllArgsConstructor
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId
    ) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);

    }


    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getAllPostsWithStatusDraft(
            @RequestAttribute UUID userId
    ) {
        User user = userService.getUserById(userId);
        List<Post> posts = postService.getPostWithDraftStatus(user);
        List<PostDto> postsDTO = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postsDTO);
    }


    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId
    ) {
        User user = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(user, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDTO updatePostRequestDTO
    ) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDTO);
        Post updatePost = postService.updatePost(id, updatePostRequest);
        PostDto postDto = postMapper.toDto(updatePost);
        return ResponseEntity.ok(
                postDto
        );
    }
}
