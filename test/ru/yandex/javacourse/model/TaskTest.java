package ru.yandex.javacourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    @DisplayName("Проверяет что таски равны если у них равный id")
    void equals_shouldBeEqual() {
        //given
        Task task1 = new Task("title", "description");
        Task task2 = new Task("title", "description");
        //when
        task1.setId(1);
        task2.setId(1);
        //then
        assertEquals(task1, task2, "Задачи не равны.");
    }
}