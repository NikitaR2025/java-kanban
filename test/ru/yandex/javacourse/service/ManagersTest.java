package ru.yandex.javacourse.service;

import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void shouldCreateTaskManager() {
        TaskManager taskManager = new InMemoryTaskManager(); {
        assertNotNull(taskManager);
        }
    }

    @Test
    void shouldCreateHistoryManager() {
        HistoryManager historyManager = new InMemoryHistoryManager(); {
            assertNotNull(historyManager);
        }
    }
}