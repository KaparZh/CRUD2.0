package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;
import com.kaparzh.crud2.service.WriterService;
import com.kaparzh.crud2.service.impl.WriterServiceImpl;

import java.util.List;

public class WriterController {

    private final WriterService writerService = new WriterServiceImpl();

    public Writer addWriter(String firstName, String lastName, List<Post> postList) {
        return writerService.create(firstName, lastName, postList);
    }

    public Writer updateWriter(Integer id, String firstName, String lastName) {
        return writerService.update(id, firstName, lastName);
    }

    public void deleteById(int id) {
        writerService.delete(id);
    }

    public Writer getById(int id) {
        return writerService.getById(id);
    }

    public List<Writer> getAll() {
        return writerService.getAll();
    }
}
