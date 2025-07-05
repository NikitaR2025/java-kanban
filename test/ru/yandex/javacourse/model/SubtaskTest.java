package ru.yandex.javacourse.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void impossibleMakeItselfEpic() {
        Subtask subtask = new Subtask("title", "description", 0);
        subtask.setId(1);
        assertFalse(subtask.setEpicId(1));
    }

    @Test
    void SubtasksEqualIfIdsEqual(){
        Subtask task1 = new Subtask("title", "description", 1);
        task1.setId(1);
        Subtask task2 = new Subtask("title2", "description2", 2);
        task2.setId(1);
        assertEquals(task1, task2, "Объекты не равны друг другу.");
    }
}