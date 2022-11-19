package com.kaparzh.crud2.controller;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.service.LabelService;
import com.kaparzh.crud2.service.impl.LabelServiceImpl;

import java.util.List;

public class LabelController {

    private final LabelService labelService = new LabelServiceImpl();

    public Label createLabel(String labelName) {
        return labelService.create(labelName);
    }

    public List<Label> getAll() {
        return labelService.getAll();
    }

    public void deleteById(int id) {
        labelService.delete(id);
    }

    public Label updateLabel(int id, String labelName) {
        return labelService.update(id, labelName);
    }

    public Label getById(int id) {
        return labelService.getById(id);
    }
}
