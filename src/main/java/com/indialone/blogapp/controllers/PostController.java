package com.indialone.blogapp.controllers;

import com.indialone.blogapp.models.ApiResponse;
import com.indialone.blogapp.models.PostResponse;
import com.indialone.blogapp.payloads.PostDTO;
import com.indialone.blogapp.services.PostService;
import com.indialone.blogapp.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{" + AppConstants.PATH_VARIABLE_USER_ID + "}/category/{" + AppConstants.PATH_VARIABLE_CATEGORY_ID + "}/posts")
    public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable(AppConstants.PATH_VARIABLE_USER_ID) Integer userId, @PathVariable(AppConstants.PATH_VARIABLE_CATEGORY_ID) Integer categoryId) {
        try {
            PostDTO createdPost = postService.createPost(postDTO, userId, categoryId);
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, createdPost), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_FAILED, false, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{" + AppConstants.PATH_VARIABLE_USER_ID + "}/posts")
    public ResponseEntity<ApiResponse> getPostsByUser(@PathVariable(AppConstants.PATH_VARIABLE_USER_ID) Integer userId, @RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_SIZE, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_BY, defaultValue = AppConstants.DEFAULT_VALUE_SORT_BY, required = false) String sortBy, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_DIR, defaultValue = AppConstants.DEFAULT_VALUE_SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, postResponse), HttpStatus.OK);
    }

    @GetMapping("/category/{" + AppConstants.PATH_VARIABLE_CATEGORY_ID + "}/posts")
    public ResponseEntity<ApiResponse> getPostsByCategory(@PathVariable(AppConstants.PATH_VARIABLE_CATEGORY_ID) Integer categoryId, @RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_SIZE, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_BY, defaultValue = AppConstants.DEFAULT_VALUE_SORT_BY, required = false) String sortBy, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_DIR, defaultValue = AppConstants.DEFAULT_VALUE_SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, postResponse), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> getAllPosts(@RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_NUMBER, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_NUMBER, required = false) Integer pageNumber, @RequestParam(value = AppConstants.REQUEST_PARAMS_PAGE_SIZE, defaultValue = AppConstants.DEFAULT_VALUE_PAGE_SIZE, required = false) Integer pageSize, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_BY, defaultValue = AppConstants.DEFAULT_VALUE_SORT_BY, required = false) String sortBy, @RequestParam(value = AppConstants.REQUEST_PARAMS_SORT_DIR, defaultValue = AppConstants.DEFAULT_VALUE_SORT_DIR, required = false) String sortDir) {
        PostResponse postResponse = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, postResponse), HttpStatus.OK);
    }

    @GetMapping("/posts/{" + AppConstants.PATH_VARIABLE_POST_ID + "}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable(AppConstants.PATH_VARIABLE_POST_ID) Integer postId) {
        PostDTO postDTO = postService.getPostById(postId);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, postDTO), HttpStatus.OK);
    }

    @PutMapping("/posts/{" + AppConstants.PATH_VARIABLE_POST_ID + "}")
    public ResponseEntity<ApiResponse> updatePostById(@Valid @RequestBody PostDTO postDTO, @PathVariable(AppConstants.PATH_VARIABLE_POST_ID) Integer postId) {
        PostDTO updatedPostDTO = postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, updatedPostDTO), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{" + AppConstants.PATH_VARIABLE_POST_ID + "}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable(AppConstants.PATH_VARIABLE_POST_ID) Integer postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, Map.of()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<ApiResponse> searchPostsByTitle(@RequestParam(AppConstants.REQUEST_PARAMS_QUERY) String query) {
        List<PostDTO> postDTOList = postService.searchPosts(query);
        return new ResponseEntity<>(new ApiResponse(AppConstants.RESPONSE_MESSAGE_SUCCESS, true, postDTOList), HttpStatus.OK);
    }

}
