package com.indialone.blogapp.impls;

import com.indialone.blogapp.exceptions.ResourceNotFoundException;
import com.indialone.blogapp.models.Category;
import com.indialone.blogapp.models.Post;
import com.indialone.blogapp.models.PostResponse;
import com.indialone.blogapp.models.User;
import com.indialone.blogapp.payloads.PostDTO;
import com.indialone.blogapp.repositories.CategoryRepo;
import com.indialone.blogapp.repositories.PostRepo;
import com.indialone.blogapp.repositories.UserRepo;
import com.indialone.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImage("default.png");
        post.setDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post createdPost = postRepo.save(post);
        return modelMapper.map(createdPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = modelMapper.map(postDTO, Post.class);
        Post availablePost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        if (post.getTitle() != null) availablePost.setTitle(post.getTitle());
        if (post.getDate() != null) availablePost.setDate(post.getDate());
        if (post.getUser() != null) availablePost.setUser(post.getUser());
        if (post.getCategory() != null) availablePost.setCategory(post.getCategory());
        if (post.getImage() != null) availablePost.setImage(post.getImage());
        if (post.getContent() != null) availablePost.setContent(post.getContent());
        Post updatedPost = postRepo.save(availablePost);
        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post availablePost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        postRepo.delete(availablePost);
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post availablePost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
        return modelMapper.map(availablePost, PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortRequest = null;

        if (sortDir.equalsIgnoreCase("asc"))
            sortRequest = Sort.by(sortBy).ascending();
        else
            sortRequest = Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sortRequest);

        Page<Post> postPage = postRepo.findAll(pageRequest);

        List<Post> listOfPosts = postPage.getContent();

        List<PostDTO> postDTOS = listOfPosts.stream().map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortRequest = null;

        if (sortDir.equalsIgnoreCase("asc")) sortRequest = Sort.by(sortBy).ascending();
        else sortRequest = Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortRequest);

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

        Page<Post> postPage = postRepo.findByUser(user, pageRequest);

        List<Post> postList = postPage.getContent();

        List<PostDTO> postsDTOList = postList.stream().map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postsDTOList);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> searchPosts(String query) {
//        List<Post> postList = postRepo.findByTitleContaining(query);
        List<Post> postList = postRepo.searchByTitle(query);
        List<PostDTO> postDTOList = postList.stream().map((post) -> modelMapper.map(post, PostDTO.class)).toList();
        return postDTOList;
    }

    @Override
    public PostResponse getAllPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortRequest = null;

        if (sortDir.equalsIgnoreCase("asc")) sortRequest = Sort.by(sortBy).ascending();
        else sortRequest = Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortRequest);

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        Page<Post> postPage = postRepo.findByCategory(category, pageRequest);

        List<Post> postList = postPage.getContent();

        List<PostDTO> postDTOList = postList.stream().map((post) -> modelMapper.map(post, PostDTO.class)).toList();

        PostResponse postResponse = new PostResponse();

        postResponse.setLastPage(postPage.isLast());
        postResponse.setContent(postDTOList);
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setPageSize(postPage.getSize());

        return postResponse;
    }
}
