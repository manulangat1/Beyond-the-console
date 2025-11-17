package com.kipchirchirlangat.blog.domain.dtos;


import com.kipchirchirlangat.blog.domain.PostStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequestDto {

    //    @NotEmpty()
    @NotBlank(message = "Must contain")
    @Size(min = 3, max = 200, message = "Message must be between {min} and {max}")
    private String title;


    @NotBlank(message = "Must contain")
    @Size(min = 3, max = 200, message = "Content must be between {min} and {max}")
    private String content;


    @NotNull(message = "Category Id required")
    private UUID categoryId;


    @Builder.Default
    @Size(max = 10, message = "Only a max of {max} tags")
    private Set<UUID> tagIds = new HashSet<>();

    @NotNull(message = "Status is required.")
    private PostStatus status;


}
