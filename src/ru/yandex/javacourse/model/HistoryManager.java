package ru.yandex.javacourse.model;

import java.util.ArrayList;

public interface HistoryManager {

    void add(Task task);

    ArrayList<Task> getHistory();

}
