package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;
import com.kaparzh.crud2.repository.WriterRepository;
import com.kaparzh.crud2.repository.impl.JdbcWriterRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class WriterController {

    private final WriterRepository writers = new JdbcWriterRepositoryImpl();

    public Writer addWriter(String firstName, String lastName, List<Post> postList) {
        return writers.save(new Writer(1, firstName, lastName, postList));
    }

    public Writer updateWriter(String firstName, String lastName) {
        return writers.update(new Writer(1, firstName, lastName, new ArrayList<>()));
    }

    public void deleteById(int id) {
        writers.deleteById(id);
    }

    public Writer getById(int id) {
        return writers.getById(id);
    }

    public List<Writer> getAll() {
        return writers.getAll();
    }
}
