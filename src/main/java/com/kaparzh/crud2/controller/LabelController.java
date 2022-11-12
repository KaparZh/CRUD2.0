package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.repository.LabelRepository;
import com.kaparzh.crud2.repository.impl.JdbcLabelRepositoryImpl;

import java.util.List;

public class LabelController {

    private final LabelRepository labels = new JdbcLabelRepositoryImpl();

    public Label createLabel(int id, String labelName) {
        return labels.save(new Label(id, labelName));
    }

    public List<Label> getAll() {
        return labels.getAll();
    }

    public void deleteById(int id) {
        labels.deleteById(id);
    }

    public Label updateLabel(int id, String labelName) {
        return labels.update(new Label(id, labelName));
    }

    public Label getById(int id) {
        return labels.getById(id);
    }
}
