package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        //given
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    @DisplayName("Проверяет, что при заполнении списка истории 11 элементов первый элемент удаляется")
    void add_add11Elements_shouldRemoveFirstElement() {
        //given
        Task task;
        for (int i = 1; i <= 10; i++) {
            task = new Task("task", "description");
            task.setId(i);
            historyManager.add(task);
        }
        //when
        task = new Task("task", "description");
        task.setId(11);
        historyManager.add(task);
        //then
        assertEquals(2, historyManager.getHistory().getFirst().getId(), "Первый элемент не удалился");
    }

    @Test
    @DisplayName("Проверяет, что метод getHistory возвращает ArrayList")
    void getHistory_shouldReturnNotNullList() {
        //when
        ArrayList<Task> listTask = historyManager.getHistory();
        //then
        assertNotNull(listTask, "Список не вернулся, равен Null");
    }
}
