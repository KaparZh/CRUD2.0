package com.kaparzh.crud2.service;

import com.kaparzh.crud2.model.Label;

public interface LabelService extends GenericService<Label, Integer> {

    Label create(String labelName);

    Label update(Integer id, String labelName);
}
