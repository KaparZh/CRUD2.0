package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.repository.PostRepository;
import com.kaparzh.crud2.repository.impl.JdbcPostRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostController {

    private final PostRepository posts = new JdbcPostRepositoryImpl();

    public Post createPost(String content, List<Label> labels) {
        long created = new Date().getTime();
        return posts.save(new Post(1, content, created, created, labels));
    }

    public Post updatePost(int id, String content) {
        long updated = new Date().getTime();
        //Временно
        return posts.update(new Post(id, content, updated, updated, new ArrayList<>()));
    }

    public void deleteById(int id) {
        posts.deleteById(id);
    }

    public Post getById(int id) {
        return posts.getById(id);
    }

    public List<Post> getAll() {
        return posts.getAll();
    }
}
