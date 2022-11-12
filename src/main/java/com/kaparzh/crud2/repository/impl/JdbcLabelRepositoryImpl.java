package com.kaparzh.crud2.repository.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.repository.LabelRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {

    private final Statement statement = JdbcConnection.getConnection();

    @Override
    public Label save(Label label) {
        try {
            statement.executeUpdate(String.format("INSERT INTO label (name) VALUES ('%s');", label.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        try {
            statement.executeUpdate(
                    String.format("UPDATE label SET name = '%s' WHERE label.id = %d;", label.getName(), label.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        try {
            statement.executeUpdate(String.format("DELETE FROM label WHERE label.id = %d;", id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Label getById(Integer id) {
        Label label = null;
        try (ResultSet rs = statement.executeQuery(String.format("SELECT id, name FROM label WHERE label.id = %d;", id))) {
            if (rs.next()) {
                label = new Label(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public List<Label> getAll() {
        ArrayList<Label> labels = new ArrayList<>();
        try (ResultSet rs = statement.executeQuery("SELECT * FROM label;")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                labels.add(new Label(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }
}
