package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int taskCounter = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int getTaskCounter() {
        return taskCounter;
    }

    // добавление задачи любого типа
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        taskCounter++;
        task.setId(taskCounter);
        if (task instanceof Epic epic) {
            epics.put(taskCounter, epic);
        } else if (task instanceof Subtask subtask) {
            subtasks.put(taskCounter, subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksIds().add(subtask.getId());
            checkStatusEpic(epic.getId());
        } else {
            tasks.put(taskCounter, task);
        }
    }

    // удаление задачи любого типа
    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.getSubtasksIds().remove(Integer.valueOf(id));
            subtasks.remove(id);
            checkStatusEpic(epic.getId());
        } else if (epics.containsKey(id)) {
            ArrayList<Integer> subtasksIds = epics.get(id).getSubtasksIds();// удаляем Epic и все его подзадачи.
            for (int subtaskId : subtasksIds) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    // обновление задачи любого типа
    public void updateTask(Task task) {
        switch (task) {
            case null -> {
                return;
            }
            case Epic epic -> {
                ArrayList<Integer> oldSubtasksIds = epics.get(epic.getId()).getSubtasksIds();
                epic.setSubtasksIds(oldSubtasksIds);
                epics.put(epic.getId(), epic);
                break;
            }
            case Subtask subtask -> {
                int oldEpicId = subtasks.get(subtask.getId()).getEpicId();
                subtask.setEpicId(oldEpicId);
                subtasks.put(subtask.getId(), subtask);
                checkStatusEpic(oldEpicId);
                break;
            }
            default -> tasks.put(task.getId(), task);
        }
    }

    // Следующие три метода - получение списка (ArrayList) всех задач соответствующего типа

    public ArrayList<Task> getTasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : tasks.keySet()) {
            listTasks.add(tasks.get(i));
        }
        return listTasks;
    }

    public ArrayList<Task> getSubtasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : subtasks.keySet()) {
            listTasks.add(subtasks.get(i));
        }
        return listTasks;
    }

    public ArrayList<Task> getEpics() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : epics.keySet()) {
            listTasks.add(epics.get(i));
        }
        return listTasks;
    }


    // следующие три метода - удаление всех задач соответствующего типа

    public void removeTasks() {
        tasks = new HashMap<>();
    }

    public void removeSubtasks() {
        subtasks = new HashMap<>();
        for (int i : epics.keySet()) { // для всех Epic очищаем списки id подзадач, и устанавливаем статус NEW
            Epic epic = epics.get(i);
            epic.setSubtasksIds(new ArrayList<>());
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public void removeEpics() {
        epics = new HashMap<>(); // удаляем все Epic и все подзадачи тоже
        subtasks = new HashMap<>();
    }

    // следующие три метода - получение по id задачи соответствующего типа

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    // получение списка (ArrayList) подзадач определённого Эпика.
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (int i : epic.getSubtasksIds()) {
            result.add(subtasks.get(i));
        }
        return result;
    }

    // расчёт статуса Epic
    public void checkStatusEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        if (subtasksIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        int counterNEW = 0;
        int counterDONE = 0;
        for (int i : subtasksIds) {
            TaskStatus status = subtasks.get(i).getStatus();
            switch (status) {
                case NEW:
                    counterNEW++;
                    break;
                case DONE:
                    counterDONE++;
                    break;
                default:
                    break;
            }
        }
        if (subtasksIds.size() == counterNEW) {
            epic.setStatus(TaskStatus.NEW);
        } else if (subtasksIds.size() == counterDONE) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }

    }

    // создание Subtask - метод для тестирования
    public Subtask createSubtask(String title, String description, String epicTitle) {
        int epicId = 0;
        for (int i : epics.keySet()) {
            if (epics.get(i).getTitle().equals(epicTitle)) {
                epicId = epics.get(i).getId();
            }
        }
        if (epicId == 0) {
            System.out.println("Эпик с таким title не найден");
            return null;
        } else {
            return new Subtask(title, description, epicId);
        }
    }
}



