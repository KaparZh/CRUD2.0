package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.service.PostService;
import com.kaparzh.crud2.service.impl.PostServiceImpl;

import java.util.List;

public class PostController {

    private final PostService postService = new PostServiceImpl();

    public Post createPost(String content, List<Label> labels) {
        return postService.create(content, labels);
    }

    public Post updatePost(int id, String content) {
        return postService.update(id, content);
    }

    public void deleteById(int id) {
        postService.delete(id);
    }

    public Post getById(int id) {
        return postService.getById(id);
    }

    public List<Post> getAll() {
        return postService.getAll();
    }
}
