package com.kaparzh.crud2.service.impl;

import com.kaparzh.crud2.model.Label;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class LabelServiceImplTest {

    private final Label label1 = new Label(1, "Yoga");
    private final Label label2 = new Label(2, "Sport");
    private final Label label3 = new Label(3, "News");

    private final List<Label> labels = List.of(label1, label2, label3);

    private final LabelServiceImpl service = mock(LabelServiceImpl.class);

    @Test
    void testGetById() {
        when(service.getById(1)).thenReturn(label1);
        assertEquals(label1, service.getById(1));
    }

    @Test
    void testGetByIdShouldReturnIncorrectValue() {
        when(service.getById(1)).thenReturn(label1);
        assertNotEquals(label2, service.getById(1));
    }

    @Test
    void testDelete() {
        service.delete(1);
        verify(service, times(1)).delete(eq(1));
    }

    @Test
    void testGetAll() {
        when(service.getAll()).thenReturn(labels);
        assertEquals(labels, service.getAll());
    }

    @Test
    void testGetAllShouldReturnIncorrectValue() {
        when(service.getAll()).thenReturn(labels);
        assertNotEquals(new ArrayList<>(), service.getAll());
    }

    @Test
    void testCreate() {
        when(service.create("NewLabel")).thenReturn(new Label(4, "NewLabel"));
        assertEquals(new Label(4, "NewLabel"), service.create("NewLabel"));
    }

    @Test
    void testCreateShouldReturnIncorrectValue() {
        when(service.create("NewLabel")).thenReturn(new Label(4, "NewLabel"));
        assertNotEquals(new Label(4, "Label"), service.create("NewLabel"));
    }

    @Test
    void testUpdate() {
        when(service.update(2, "UpdateLabel")).thenReturn(new Label(2, "UpdateLabel"));
        assertEquals(new Label(2, "UpdateLabel"), service.update(2, "UpdateLabel"));
    }

    @Test
    void testUpdateShouldReturnIncorrectValue() {
        when(service.update(2, "UpdateLabel")).thenReturn(new Label(2, "UpdateLabel"));
        assertNotEquals(new Label(2, "Label"), service.update(2, "UpdateLabel"));
    }
}