package ru.yandex.javacourse.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test @DisplayName("Проверка создаётся ли TaskManager")
    void getDefault_createTaskManager_shouldBeNotNull() {
        TaskManager taskManager = new InMemoryTaskManager(); {
        assertNotNull(taskManager, "taskManager = Null");
        }
    }

    @Test @DisplayName("Проверка создаётся ли HistoryManager")
    void getDefaultHistory_createHistoryManager_shouldBeNotNull() {
        HistoryManager historyManager = new InMemoryHistoryManager(); {
            assertNotNull(historyManager, "historyManager = Null");
        }
    }
}