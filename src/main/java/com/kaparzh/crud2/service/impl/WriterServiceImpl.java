package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;
import com.kaparzh.crud2.repository.WriterRepository;
import com.kaparzh.crud2.repository.impl.JdbcWriterRepositoryImpl;
import com.kaparzh.crud2.service.WriterService;

import java.util.ArrayList;
import java.util.List;

public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository = new JdbcWriterRepositoryImpl();

    @Override
    public Writer getById(Integer id) {
        return writerRepository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        writerRepository.deleteById(id);
    }

    @Override
    public List<Writer> getAll() {
        return writerRepository.getAll();
    }

    @Override
    public Writer create(String firstName, String lastName, List<Post> postList) {
        return writerRepository.save(new Writer(1, firstName, lastName, postList));
    }

    @Override
    public Writer update(Integer id,String firstName, String lastName) {
        return writerRepository.update(new Writer(id, firstName, lastName, new ArrayList<>()));
    }
}
