package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Post;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    private final Post post1 = new Post(1, "Post#1", 1L, 1L, new ArrayList<>());
    private final Post post2 = new Post(2, "Post#2", 1L, 1L, new ArrayList<>());
    private final Post post3 = new Post(3, "Post#3", 1L, 1L, new ArrayList<>());

    private final List<Post> posts = List.of(post1, post2, post3);

    private final PostServiceImpl service = mock(PostServiceImpl.class);

    @Test
    void testGetById() {
        when(service.getById(1)).thenReturn(post1);
        assertEquals(post1, service.getById(1));
    }

    @Test
    void testGetByIdShouldReturnIncorrectValue() {
        when(service.getById(1)).thenReturn(post1);
        assertNotEquals(post2, service.getById(1));
    }

    @Test
    void testDelete() {
        service.delete(1);
        verify(service, times(1)).delete(eq(1));
    }

    @Test
    void testGetAll() {
        when(service.getAll()).thenReturn(posts);
        assertEquals(posts, service.getAll());
    }

    @Test
    void testGetAllShouldReturnIncorrectValue() {
        when(service.getAll()).thenReturn(posts);
        assertNotEquals(new ArrayList<>(), service.getAll());
    }

    @Test
    void testCreate() {
        Post newPost = new Post(4, "NewPost", 2L, 2L, new ArrayList<>());
        when(service.create("NewPost", new ArrayList<>())).thenReturn(newPost);
        assertEquals(newPost, service.create("NewPost", new ArrayList<>()));
    }

    @Test
    void testCreateShouldReturnIncorrectValue() {
        Post newPost = new Post(4, "NewPost", 2L, 2L, new ArrayList<>());
        when(service.create("NewPost", new ArrayList<>())).thenReturn(newPost);
        assertNotEquals(post1, service.create("NewPost", new ArrayList<>()));
    }

    @Test
    void testUpdate() {
        Post updatedPost = new Post(2, "UpdatedPost", 1L, 1L, new ArrayList<>());
        when(service.update(2, "UpdatedPost")).thenReturn(updatedPost);
        assertEquals(updatedPost, service.update(2, "UpdatedPost"));
    }

    @Test
    void testUpdateShouldReturnIncorrectValue() {
        Post updatedPost = new Post(2, "UpdatedPost", 1L, 1L, new ArrayList<>());
        when(service.update(2, "UpdatedPost")).thenReturn(updatedPost);
        assertNotEquals(post1, service.update(2, "UpdatedPost"));
    }
}