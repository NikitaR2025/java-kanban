package ru.yandex.javacourse.model;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int getTaskCounter();

    // добавление задачи любого типа
    void addTask(Task task);

    // удаление задачи любого типа
    void removeTask(int id);

    // обновление задачи любого типа
    void updateTask(Task task);

    // Следующие три метода - получение списка (ArrayList) всех задач соответствующего типа

    ArrayList<Task> getTasks();

    ArrayList<Task> getSubtasks();

    ArrayList<Task> getEpics();

    // следующие три метода - удаление всех задач соответствующего типа

    void removeTasks();

    void removeSubtasks();

    void removeEpics();

    // следующие три метода - получение по id задачи соответствующего типа

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    // получение списка (ArrayList) подзадач определённого Эпика.
    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    // расчёт статуса Epic
    void checkStatusEpic(int id);

    List<Task> getHistory();

}
