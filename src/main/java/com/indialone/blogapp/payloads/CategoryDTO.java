package com.indialone.blogapp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private int categoryId;

    @NotEmpty
    @Size(min = 2,message = "category title should be greater than 2 characters.")
    private String categoryTitle;

    @NotEmpty
    @Size(min = 5, message = "category description should be greater than 5 characters.")
    private String categoryDescription;

}
