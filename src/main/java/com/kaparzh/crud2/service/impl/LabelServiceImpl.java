package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.repository.LabelRepository;
import com.kaparzh.crud2.repository.impl.JdbcLabelRepositoryImpl;
import com.kaparzh.crud2.service.LabelService;

import java.util.List;

public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository = new JdbcLabelRepositoryImpl();

    @Override
    public Label getById(Integer id) {
        return labelRepository.getById(id);
    }

    @Override
    public void delete(Integer id) {
        labelRepository.deleteById(id);
    }

    @Override
    public List<Label> getAll() {
        return labelRepository.getAll();
    }

    @Override
    public Label create(String labelName) {
        return labelRepository.save(new Label(1, labelName));
    }

    @Override
    public Label update(Integer id, String labelName) {
        return labelRepository.update(new Label(id, labelName));
    }
}
