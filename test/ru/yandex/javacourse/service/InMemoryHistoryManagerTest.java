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
    @DisplayName("Проверяет, что метод getHistory возвращает ArrayList")
    void getHistory_shouldReturnNotNullList() {
        //when
        ArrayList<Task> listTask = historyManager.getHistory();
        //then
        assertNotNull(listTask, "Список не вернулся, равен Null");
    }
}
