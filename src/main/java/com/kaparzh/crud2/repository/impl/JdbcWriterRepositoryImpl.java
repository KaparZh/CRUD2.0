package com.kaparzh.crud2.repository.impl;

import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.model.Writer;
import com.kaparzh.crud2.repository.WriterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private final JdbcPostRepositoryImpl postRepository = new JdbcPostRepositoryImpl();
    private final Connection connection = JdbcConnection.getConnection();

    @Override
    public Writer save(Writer writer) {
        String sql = "INSERT INTO writer (first_name, last_name) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    writer.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO writer_post (writer_id, post_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Post post : writer.getPosts()) {
                preparedStatement.setInt(1, writer.getId());
                preparedStatement.setInt(2, post.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        String sql = "UPDATE writer SET first_name = ?, last_name = ? WHERE writer.id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    writer.setId(resultSet.getInt(1));
                }
            }

            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setInt(3, writer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getById(writer.getId());
    }

    @Override
    public void deleteById(Integer id) {
        String sql = String.format("DELETE FROM writer WHERE writer.id = %d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.addBatch(sql);
            sql = String.format("DELETE FROM writer_post WHERE writer_id = %d;", id);
            statement.addBatch(sql);
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getById(Integer id) {
        Writer writer = new Writer();
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT w.id, w.first_name, w.last_name, wp.post_id\n" +
                "FROM writer As w\n" +
                "LEFT JOIN writer_post wp ON w.id = wp.writer_id\n" +
                "WHERE w.id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    if (writer.getId() == null) {
                        writer.setId(rs.getInt(1));
                        writer.setFirstName(rs.getString(2));
                        writer.setLastName(rs.getString(3));
                    }
                    int postId = rs.getInt(4);
                    posts.add(postRepository.getById(postId));
                }
                writer.setPosts(posts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer.getId() == null ? null : writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        String sql = "SELECT w.id, w.first_name, w.last_name, wp.post_id\n" +
                "FROM writer As w\n" +
                "LEFT JOIN writer_post wp on w.id = wp.writer_id;";

        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            Writer writer = new Writer();
            Set<Integer> uniqueWriterIdSet = new HashSet<>();

            while (rs.next()) {
                if (uniqueWriterIdSet.add(rs.getInt(1))) {
                    if (writer.getId() != null) {
                        writer.setPosts(posts);
                        writers.add(writer);
                        posts = new ArrayList<>();
                        writer = new Writer();
                    }
                    writer.setId(rs.getInt(1));
                    writer.setFirstName(rs.getString(2));
                    writer.setLastName(rs.getString(3));
                }
                int postId = rs.getInt(4);
                if (postId != 0) {
                    posts.add(postRepository.getById(postId));
                }
            }
            writer.setPosts(posts);
            writers.add(writer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }
}
