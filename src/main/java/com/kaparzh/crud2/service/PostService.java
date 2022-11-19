package com.kaparzh.crud2.service;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;

import java.util.List;

public interface PostService extends GenericService<Post, Integer> {

    Post create(String content, List<Label> labels);

    Post update(Integer id, String content);
}
