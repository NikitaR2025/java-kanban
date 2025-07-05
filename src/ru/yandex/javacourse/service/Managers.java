package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }


}
