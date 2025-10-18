package ru.yandex.javacourse.service;

import ru.yandex.javacourse.exceptions.ManagerLoadException;
import ru.yandex.javacourse.exceptions.ManagerSaveException;
import ru.yandex.javacourse.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static ru.yandex.javacourse.model.TaskTypes.*;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
        if (Files.exists(file.toPath())) {
            loadFromFile();
        } else {
            try {
                Files.createFile(file.toPath());
            } catch (IOException e) {
                throw new ManagerSaveException("Файл не существует, ошибка при попытке создания файла");
            }
        }
    }

    public static void main(String[] args) {
        FileBackedTaskManager manager = Managers.loadFromFile(new File("data.txt"));

        // Создадим задачи, эпики, подзадачи
        Task task0 = new Task("Уход за котом", "Покормить выгулять");
        manager.addTask(task0);
        Epic epic0 = new Epic("Уборка", "пора наводить порядок");
        manager.addTask(epic0);
        Subtask epic0subtask0 = manager.createSubtask("Стирка", "шорты футболка летняя шапка", "Уборка");
        manager.addTask(epic0subtask0);

        System.out.println("Содержимое manager:");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        // Создадим новый FileBackedTaskManager из этого же файла, и выведем его содержимое на экран:
        FileBackedTaskManager manager1 = Managers.loadFromFile(new File("data.txt"));

        System.out.println("Содержимое manager1:");
        System.out.println(manager1.getTasks());
        System.out.println(manager1.getEpics());
        System.out.println(manager1.getSubtasks());
    }

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
    public void save() {
        try (BufferedWriter buff = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, false))) {

            String headings = "id,type,name,status,description,epic";
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
            case Subtask sub -> taskType = SUBTASK;
            case Epic ep -> taskType = EPIC;
            default -> taskType = TASK;
        }

        StringBuilder builder = new StringBuilder(String.format("\n%d,%s,%s,%s,%s,", task.getId(), taskType,
                task.getTitle(), task.getStatus(), task.getDescription()));

        if (task instanceof Subtask subtask) {
            builder.append(subtask.getEpicId());
        }

        return builder.toString();
    }

    // создание Task из строки
    private Task fromString(String string) {
        Task task = null;
        String[] elements = string.split(",");
        TaskTypes type = valueOf(elements[1]);
        switch (type) {
            case TASK -> {
                task = new Task(Integer.parseInt(elements[0]), elements[2], TaskStatus.valueOf(elements[3]), elements[4]);
            }
            case EPIC -> {
                task = new Epic(Integer.parseInt(elements[0]), elements[2], TaskStatus.valueOf(elements[3]),
                        elements[4]);
            }
            case SUBTASK -> {
                task = new Subtask(Integer.parseInt(elements[0]), elements[2], TaskStatus.valueOf(elements[3]),
                        elements[4], Integer.parseInt(elements[5]));
            }
        }
        return task;
    }

    // Загрузка данных из файла. Метод private, используется в конструкторе.
    private void loadFromFile() {
        try {
            String data = Files.readString(file.toPath());
            if (data.isBlank()) {
                return;
            }
            String[] strings = data.split("\n");

            for (int i = 1; i < strings.length; i++) {
                Task task = fromString(strings[i]);
                if (task.getId() > taskCounter) {
                    taskCounter = task.getId();
                }
                if (task instanceof Epic epic) {
                    epics.put(epic.getId(), epic);
                } else if (task instanceof Subtask subtask) {
                    subtasks.put(subtask.getId(), subtask);
                    Epic epic = epics.get(subtask.getEpicId());
                    epic.addSubtaskId(subtask.getId());
                } else {
                    tasks.put(task.getId(), task);
                }
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при загрузке из файла");
        }
    }
}
