package com.kaparzh.crud2.repository.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcPostRepositoryImpl implements PostRepository {

    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Post save(Post post) {
        String sql = "INSERT INTO post (content, created, updated) VALUES (?, NOW(), NOW());";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    post.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Label label : post.getLabels()) {
                preparedStatement.setInt(1, post.getId());
                preparedStatement.setInt(2, label.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        String sql = "UPDATE post SET content = ?, updated = NOW() WHERE post.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    post.setId(resultSet.getInt(1));
                }
            }

            preparedStatement.setString(1, post.getContent());
            preparedStatement.setInt(2, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getById(post.getId());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = String.format("DELETE FROM post WHERE post.id = %d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.addBatch(sql);
            sql = String.format("DELETE FROM post_label WHERE post_id = %d;", id);
            statement.addBatch(sql);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post getById(Integer id) {
        Post post = new Post();
        List<Label> labels = new ArrayList<>();

        String sql = "SELECT p.id, p.content, p.created, p.updated, l.id, l.name\n" +
                "FROM post As p\n" +
                "LEFT JOIN Post_label Pl on p.id = Pl.post_id\n" +
                "LEFT JOIN label l on l.id = Pl.label_id\n" +
                "WHERE p.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    if (post.getId() == null) {
                        post.setId(rs.getInt(1));
                        post.setContent(rs.getString(2));
                        post.setCreated(Timestamp.valueOf(rs.getString(3)).getTime());
                        post.setUpdated(Timestamp.valueOf(rs.getString(4)).getTime());
                    }

                    int labelId = rs.getInt(5);
                    String labelName = rs.getString(6);
                    if (labelId != 0) {
                        labels.add(new Label(labelId, labelName));
                    }
                }
                post.setLabels(labels);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        List<Label> labels = new ArrayList<>();

        String sql = "SELECT p.id, p.content, p.created, p.updated, l.id, l.name " +
                "FROM post As p " +
                "LEFT JOIN Post_label Pl on p.id = Pl.post_id " +
                "LEFT JOIN label l on l.id = Pl.label_id;";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            Set<Integer> postIdSet = new HashSet<>();
            Post post = new Post();
            while (rs.next()) {
                if (postIdSet.add(rs.getInt(1))) {
                    if (post.getId() != null) {
                        post.setLabels(labels);
                        postList.add(post);
                        labels = new ArrayList<>();
                        post = new Post();
                    }
                    post.setId(rs.getInt(1));
                    post.setContent(rs.getString(2));
                    post.setCreated(Timestamp.valueOf(rs.getString(3)).getTime());
                    post.setUpdated(Timestamp.valueOf(rs.getString(4)).getTime());
                }

                int labelId = rs.getInt(5);
                String labelName = rs.getString(6);
                if (labelId != 0) {
                    labels.add(new Label(labelId, labelName));
                }
            }
            post.setLabels(labels);
            postList.add(post);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return postList;
    }
}
