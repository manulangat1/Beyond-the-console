package com.kipchirchirlangat.blog.services.impl;

import com.kipchirchirlangat.blog.domain.CreatePostRequest;
import com.kipchirchirlangat.blog.domain.PostStatus;
import com.kipchirchirlangat.blog.domain.UpdatePostRequest;
import com.kipchirchirlangat.blog.domain.entities.Category;
import com.kipchirchirlangat.blog.domain.entities.Post;
import com.kipchirchirlangat.blog.domain.entities.Tag;
import com.kipchirchirlangat.blog.domain.entities.User;
import com.kipchirchirlangat.blog.repositories.PostRepository;
import com.kipchirchirlangat.blog.services.CategoryService;
import com.kipchirchirlangat.blog.services.PostService;
import com.kipchirchirlangat.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final int WORDS_PER_MINUTE = 200;
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
//        return List.of();

        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagContaining(PostStatus.PUBLISHED, category, tag);
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTag(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }


    @Override
    public List<Post> getPostWithDraftStatus(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
//        return null;
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
//        newPost.setCategory();


        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);


        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagsById(tagIds);
        newPost.setTags(new HashSet<>(tags));


        return postRepository.save(newPost);

    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post does not exist with id" + id));
        existPost.setTitle(updatePostRequest.getTitle());
        existPost.setStatus(updatePostRequest.getStatus());
        existPost.setContent(updatePostRequest.getContent());
//       TODO: come and do the rest

        return postRepository.save(existPost);
    }


    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;

        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);


    }

}
