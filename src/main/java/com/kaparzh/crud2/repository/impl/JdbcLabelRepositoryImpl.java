package com.kaparzh.crud2.repository.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {

    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Label save(Label label) {
        String sql = "INSERT INTO label (name) VALUES (?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    label.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public Label update(Label label) {
        String sql = "UPDATE label SET name = ? WHERE label.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setInt(2, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM label WHERE label.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Label getById(Integer id) {
        Label label = null;
        String sql = "SELECT id, name FROM label WHERE label.id = %d;";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format(sql, id))) {

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
        List<Label> labels = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM label;")) {

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
