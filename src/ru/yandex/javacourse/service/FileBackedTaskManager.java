package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.ManagerSaveException;
import ru.yandex.javacourse.model.*;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final Path path = Paths.get("data.txt");
    private final String headings = "id,type,name,status,description,epic\n";

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        save();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
        save();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
        save();
    }

    // сохранение всех задач в файл
    private void save() {
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(path.toFile(), StandardCharsets.UTF_8, false))) {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            buff.write(headings);

            for (Integer key : tasks.keySet()) {
                Task task = tasks.get(key);
                buff.write(taskToString(task));
            }

            for (Integer key : epics.keySet()) {
                Task task = epics.get(key);
                buff.write(taskToString(task));
            }

            for (Integer key : subtasks.keySet()) {
                Task task = subtasks.get(key);
                buff.write(taskToString(task));
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    // Превращение Task в строку для записи в файл
    private String taskToString(Task task) {
        Enum taskType;
        switch (task) {
            case Subtask sub -> taskType = TaskTypes.SUBTASK;
            case Epic ep -> taskType = TaskTypes.EPIC;
            default -> taskType = TaskTypes.TASK;
        }

        StringBuilder builder = new StringBuilder(String.format("%d,%s,%s,%s,%s,", task.getId(), taskType, task.getTitle(), task.getStatus(), task.getDescription()));

        if (task instanceof Subtask subtask) {
            builder.append(subtask.getEpicId());
        }

        builder.append("\n");
        return builder.toString();
    }


}
