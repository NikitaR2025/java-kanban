package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.Epic;
import ru.yandex.javacourse.model.Subtask;
import ru.yandex.javacourse.model.Task;
import ru.yandex.javacourse.model.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    TaskManager manager;
    Task task;
    Epic epic;
    Subtask subtask;

    @BeforeEach
    void createManager() {
        manager = Managers.getDefault();
        task = new Task("title", "description");
        manager.addTask(task);
        epic = new Epic("title", "description");
        manager.addTask(epic);
        subtask = new Subtask("title", "description", 2);
        manager.addTask(subtask);
    }

    @Test
    void addTask(){
        assertNotNull(manager.getTasks(), "Список задач не возвращается");
        assertEquals(1, manager.getTasks().size(), "Список задач не должен быть пуст");
        assertNotNull(manager.getSubtasks(), "Список Подзадач не возвращается");
        assertEquals(1, manager.getSubtasks().size(), "Список Подзадач не должен быть пуст");
        assertNotNull(manager.getEpics(), "Список Эпиков не возвращается");
        assertEquals(1, manager.getEpics().size(), "Список Эпиков не должен быть пуст");
    }

    @Test
    void updateTask(){
        int listTasksSize = manager.getTasks().size();
        task.setDescription("Изменен description");
        manager.updateTask(task);
        assertEquals(listTasksSize, manager.getTasks().size(), "Изменилось количество задач");
    }

    @Test
    void removeTask(){
        int listTasksSize = manager.getTasks().size();
        manager.removeTask(1);
        assertEquals(--listTasksSize, manager.getTasks().size(), "Не уменьшилось количество задач");
    }

    @Test
    void removeTasks(){
        manager.removeTasks();
        assertTrue(manager.getTasks().isEmpty(), "Список Задач не очищен");
        manager.removeSubtasks();
        assertTrue(manager.getSubtasks().isEmpty(), "Список Подзадач не очищен");
        manager.removeEpics();
        assertTrue(manager.getEpics().isEmpty(), "Список Эпиков не очищен");
    }


    @Test
    void shouldReturnEqualFieldsInTask() {
        assertEquals(task.toString(), manager.getTask(1).toString(), "Поля сохраненной  и полученной Задачи не совпадают");
        assertEquals(subtask.toString(), manager.getSubtask(3).toString(), "Поля сохраненной  и полученной Подзадачи не совпадают");
        assertEquals(epic.toString(), manager.getEpic(2).toString(), "Поля сохраненного  и полученного Эпика не совпадают");

    }

    @Test
    void getSubtasksOfEpic(){
        assertNotNull(manager.getSubtasksOfEpic(2), "Список подзадач эпика не возвращается");
        assertNotEquals(0, manager.getSubtasksOfEpic(2).size(), "Список подзадач не должен быть пуст");
    }

    @Test
    void shouldBeEqualInHistory(){
        manager.getTask(1);
        manager.getSubtask(3);
        assertEquals(subtask, manager.getHistory().getLast(), "Неправильная последняя задача в истории");
    }




}