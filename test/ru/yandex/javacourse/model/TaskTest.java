package ru.yandex.javacourse.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void tasksEqualIfIdsEqual(){
        Task task1 = new Task("title", "description");
        task1.setId(1);
        Task task2 = new Task("title", "description");
        task2.setId(1);
        assertEquals(task1, task2, "Задачи не равны.");
    }

}