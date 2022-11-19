package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.repository.PostRepository;
import com.kaparzh.crud2.repository.impl.JdbcPostRepositoryImpl;
import com.kaparzh.crud2.service.PostService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository = new JdbcPostRepositoryImpl();

    @Override
    public Post getById(Integer id) {
        return postRepository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post create(String content, List<Label> labels) {
        long created = new Date().getTime();
        return postRepository.save(new Post(1, content, created, created, labels));
    }

    @Override
    public Post update(Integer id, String content) {
        long updated = new Date().getTime();
        return postRepository.update(new Post(id, content, updated, updated, new ArrayList<>()));
    }
}
