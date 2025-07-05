package ru.yandex.javacourse.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void shouldBeImpossibleAddEpicItselfAsSubtask(){
        Epic epic = new Epic("title", "description");
        epic.setId(1);
        assertFalse(epic.addSubtaskId(1));
    }

    @Test
    void ShouldBeEqualEpicsIfIdsEqual(){
        Epic epic1 = new Epic("title", "description");
        epic1.setId(1);
        Epic epic2 = new Epic("title2", "description2");
        epic2.setId(1);
        assertEquals(epic1, epic2, "Объекты не равны друг другу.");
    }


}