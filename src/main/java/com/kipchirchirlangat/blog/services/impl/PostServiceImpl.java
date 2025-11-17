package com.kipchirchirlangat.blog.services.impl;

import com.kipchirchirlangat.blog.domain.PostStatus;
import com.kipchirchirlangat.blog.domain.entities.Category;
import com.kipchirchirlangat.blog.domain.entities.Post;
import com.kipchirchirlangat.blog.domain.entities.Tag;
import com.kipchirchirlangat.blog.repositories.PostRepository;
import com.kipchirchirlangat.blog.services.CategoryService;
import com.kipchirchirlangat.blog.services.PostService;
import com.kipchirchirlangat.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
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
}
