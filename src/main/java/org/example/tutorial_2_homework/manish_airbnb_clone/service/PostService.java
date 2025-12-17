package org.example.tutorial_2_homework.manish_airbnb_clone.service;


import org.example.tutorial_2_homework.manish_airbnb_clone.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);
}
