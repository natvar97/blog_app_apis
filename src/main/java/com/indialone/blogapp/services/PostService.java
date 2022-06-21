package com.indialone.blogapp.services;

import com.indialone.blogapp.models.Post;
import com.indialone.blogapp.models.PostResponse;
import com.indialone.blogapp.models.User;
import com.indialone.blogapp.payloads.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    PostDTO updatePost(PostDTO postDTO, Integer postId);

    void deletePost(Integer postId);

    PostDTO getPostById(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDTO> searchPosts(String query);

    PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
