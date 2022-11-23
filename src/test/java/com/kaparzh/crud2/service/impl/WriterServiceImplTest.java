package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Writer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WriterServiceImplTest {

    private final Writer writer1 = new Writer(1, "Ivan", "Ivanov", new ArrayList<>());
    private final Writer writer2 = new Writer(2, "Sergey", "Sergeev", new ArrayList<>());
    private final Writer writer3 = new Writer(3, "Petr", "Petrov", new ArrayList<>());

    private final List<Writer> writers = List.of(writer1, writer2, writer3);

    private final WriterServiceImpl service = mock(WriterServiceImpl.class);

    @Test
    void testGetById() {
        when(service.getById(1)).thenReturn(writer1);
        assertEquals(writer1, service.getById(1));
    }

    @Test
    void testGetByIdShouldReturnIncorrectValue() {
        when(service.getById(1)).thenReturn(writer1);
        assertNotEquals(writer2, service.getById(1));
    }

    @Test
    void testDelete() {
        service.delete(1);
        verify(service, times(1)).delete(eq(1));
    }

    @Test
    void testGetAll() {
        when(service.getAll()).thenReturn(writers);
        assertEquals(writers, service.getAll());
    }

    @Test
    void testGetAllShouldReturnIncorrectValue() {
        when(service.getAll()).thenReturn(writers);
        assertNotEquals(new ArrayList<>(), service.getAll());
    }

    @Test
    void testCreate() {
        Writer newWriter = new Writer(4, "NewWriter", "New", new ArrayList<>());
        when(service.create("NewWriter", "New", new ArrayList<>())).thenReturn(newWriter);
        assertEquals(newWriter, service.create("NewWriter", "New", new ArrayList<>()));
    }

    @Test
    void testCreateShouldReturnIncorrectValue() {
        Writer newWriter = new Writer(4, "NewWriter", "New", new ArrayList<>());
        when(service.create("NewWriter", "New", new ArrayList<>())).thenReturn(newWriter);
        assertNotEquals(writer1, service.create("NewWriter", "New", new ArrayList<>()));
    }

    @Test
    void testUpdate() {
        Writer updatedWriter = new Writer(2, "UpdatedName", "UpdatedName", new ArrayList<>());
        when(service.update(2, "UpdatedName", "UpdatedName")).thenReturn(updatedWriter);
        assertEquals(updatedWriter, service.update(2, "UpdatedName", "UpdatedName"));
    }

    @Test
    void testUpdateShouldReturnIncorrectValue() {
        Writer updatedWriter = new Writer(2, "UpdatedName", "UpdatedName", new ArrayList<>());
        when(service.update(2, "UpdatedName", "UpdatedName")).thenReturn(updatedWriter);
        assertNotEquals(writer1, service.update(2, "UpdatedName", "UpdatedName"));
    }
}