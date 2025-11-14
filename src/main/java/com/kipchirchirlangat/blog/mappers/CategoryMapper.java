package com.kipchirchirlangat.blog.mappers;


import com.kipchirchirlangat.blog.domain.PostStatus;
import com.kipchirchirlangat.blog.domain.dtos.CategoryDto;
import com.kipchirchirlangat.blog.domain.dtos.CreateCategoryDto;
import com.kipchirchirlangat.blog.domain.entities.Category;
import com.kipchirchirlangat.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);


    Category toEntity(CreateCategoryDto createCategoryDto);


    @Named("calculatePostCount")
    default  long calculatePostCount(List<Post> posts) {
        if (null == posts) {
            return  0;
        }
       return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}
