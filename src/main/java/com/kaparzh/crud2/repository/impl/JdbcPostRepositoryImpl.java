package com.kaparzh.crud2.repository.impl;

import com.kaparzh.crud2.model.Label;
import com.kaparzh.crud2.model.Post;
import com.kaparzh.crud2.repository.PostRepository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JdbcPostRepositoryImpl implements PostRepository {

    @Override
    public Post save(Post post) {
        try (Statement statement = JdbcConnection.getConnection()) {
            statement.execute(String.format(
                    "INSERT INTO post (content, created, updated) VALUES ('%s', NOW(), NOW());", post.getContent()));
            for (Label label : post.getLabels()) {
                statement.execute(String.format(
                        "INSERT INTO post_label (post_id, label_id) VALUES (%d, %d)", post.getId(), label.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        try (Statement statement = JdbcConnection.getConnection()) {
            statement.executeUpdate(String.format("UPDATE post SET content = '%s' WHERE post.id = %d;", post.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public void deleteById(Integer id) {
        List<Post> postList = getAllPostsInternal();
        postList.removeIf(post -> post.getId().equals(id));
        writePostList(postList);
    }

    @Override
    public Post getById(Integer id) {
        return getAllPostsInternal().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    private Integer generateId(List<Post> posts) {
        Post maxIdPost = posts.stream().max(Comparator.comparing(Post::getId)).orElse(null);
        return Objects.nonNull(maxIdPost) ? maxIdPost.getId() + 1 : 1;
    }

    private List<Post> getAllPostsInternal() {
//        try {
//            Type listType = new TypeToken<ArrayList<Post>>() {}.getType();
//            return GSON.fromJson(new JsonReader(new FileReader(JSON_PATH)), listType);
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
////            return Collections.emptyList();
        return new ArrayList<>();

    }

    private void writePostList(List<Post> postList) {
//        String json = GSON.toJson(postList);
//        try (FileWriter fw = new FileWriter(JSON_PATH)) {
//            fw.write(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
