package com.kipchirchirlangat.blog.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {


    @NotBlank( message = "Category name is required")
    @Size(min=2, max = 50, message = "Name must be between ${min} and ${max} characters")
//    @Pattern(regexp = )
    private String name;
}
