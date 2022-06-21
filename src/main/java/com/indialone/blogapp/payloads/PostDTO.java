package com.indialone.blogapp.payloads;

import com.indialone.blogapp.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@Setter
@Getter
public class PostDTO {

    private int postId;

    @NotEmpty
    @Size(min = 3, message = "post title should be greater than 3 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "post content should be greater than 10 characters")
    private String content;

    private String image;

    private Date date;

    private CategoryDTO category;

    private UserDTO user;

}
