package com.kaparzh.crud2.service;

import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;

import java.util.List;

public interface WriterService extends GenericService<Writer, Integer> {

    Writer create(String firstName, String lastName, List<Post> postList);

    Writer update(Integer id, String firstName, String lastName);
}
