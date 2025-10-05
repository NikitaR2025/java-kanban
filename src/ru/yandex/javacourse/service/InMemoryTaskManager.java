package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected int taskCounter = 0;
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int getTaskCounter() {
        return taskCounter;
    }

    // добавление задачи любого типа
    @Override
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
            epic.addSubtaskId(subtask.getId());
            checkStatusEpic(epic.getId());
        } else {
            tasks.put(taskCounter, task);
        }
    }

    // удаление задачи любого типа
    @Override
    public void removeTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (subtasks.containsKey(id)) {
            Epic epic = epics.get(subtasks.get(id).getEpicId());
            epic.removeSubtaskId(id);
            subtasks.remove(id);
            checkStatusEpic(epic.getId());
        } else if (epics.containsKey(id)) {
            ArrayList<Integer> subtasksIds = epics.get(id).getSubtasksIds();// удаляем Epic и все его подзадачи.
            for (int subtaskId : subtasksIds) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            epics.remove(id);
        }
        historyManager.remove(id);
    }

    // обновление задачи любого типа
    @Override
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

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : tasks.keySet()) {
            listTasks.add(tasks.get(i));
        }
        return listTasks;
    }

    @Override
    public ArrayList<Task> getSubtasks() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : subtasks.keySet()) {
            listTasks.add(subtasks.get(i));
        }
        return listTasks;
    }

    @Override
    public ArrayList<Task> getEpics() {
        ArrayList<Task> listTasks = new ArrayList<>();
        for (int i : epics.keySet()) {
            listTasks.add(epics.get(i));
        }
        return listTasks;
    }

    @Override
    public void removeTasks() {
        for (int id : tasks.keySet()) { //удаляем таски из истории просмотров
            historyManager.remove(id);
        }
        tasks = new HashMap<>();
    }

    @Override
    public void removeSubtasks() {
        for (int id : subtasks.keySet()) { //удаляем подзадачи из истории просмотров
            historyManager.remove(id);
        }
        subtasks = new HashMap<>();
        for (int i : epics.keySet()) { // для всех Epic очищаем списки id подзадач, и устанавливаем статус NEW
            Epic epic = epics.get(i);
            epic.setSubtasksIds(new ArrayList<>());
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public void removeEpics() {
        for (int id : epics.keySet()) { //удаляем все эпики из истории просмотров
            historyManager.remove(id);
        }
        for (int id : subtasks.keySet()) { //удаляем все подзадачи из истории просмотров
            historyManager.remove(id);
        }
        epics = new HashMap<>(); // удаляем все эпики и все подзадачи тоже
        subtasks = new HashMap<>();
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (int i : epic.getSubtasksIds()) {
            result.add(subtasks.get(i));
        }
        return result;
    }

    @Override
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

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
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



