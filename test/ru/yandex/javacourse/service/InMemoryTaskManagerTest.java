package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    int listTasksSize;

    @BeforeEach
    @DisplayName("Инициализирует manager, создает task, epic, subtask и добавляет их в manager")
    void setUp() {
        manager = Managers.getDefault();
        task = new Task("title", "description");
        manager.addTask(task);
        epic = new Epic("title", "description");
        manager.addTask(epic);
        subtask = new Subtask("title", "description", 2);
        manager.addTask(subtask);
    }

    @Test
    @DisplayName("Проверяет что при добавлении Task, Subtask, Epic увеличивается длина соответствующего списка задач")
    void addTask() {
        listTasksSize = manager.getTasks().size();
        manager.addTask(task);
        assertEquals(++listTasksSize, manager.getTasks().size(), "Не увеличилась длина списка Task");
        listTasksSize = manager.getSubtasks().size();
        manager.addTask(subtask);
        assertEquals(++listTasksSize, manager.getSubtasks().size(), "Не увеличилась длина списка Subtask");
        listTasksSize = manager.getEpics().size();
        manager.addTask(epic);
        assertEquals(++listTasksSize, manager.getEpics().size(), "Не увеличилась длина списка Epics");
    }


    @Test
    @DisplayName("Проверяет, что методы getTasks, getSubtasks, getEpics возвращают списки задач, и списки не пустые")
    void getTasks() {
        assertNotNull(manager.getTasks(), "Список задач не возвращается");
        assertEquals(1, manager.getTasks().size(), "Список задач не должен быть пуст");
        assertNotNull(manager.getSubtasks(), "Список Подзадач не возвращается");
        assertEquals(1, manager.getSubtasks().size(), "Список Подзадач не должен быть пуст");
        assertNotNull(manager.getEpics(), "Список Эпиков не возвращается");
        assertEquals(1, manager.getEpics().size(), "Список Эпиков не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода updateTask количество задач в списке не изменяется")
    void updateTask() {
        listTasksSize = manager.getTasks().size();
        task.setDescription("Изменен description");
        manager.updateTask(task);
        assertEquals(listTasksSize, manager.getTasks().size(), "Изменилось количество задач");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода removeTask количество задач уменьшилось на 1")
    void removeTask() {
        listTasksSize = manager.getTasks().size();
        manager.removeTask(1);
        assertEquals(--listTasksSize, manager.getTasks().size(), "Не уменьшилось количество задач");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода removeTasks все списки задач становятся пустыми")
    void removeTasks() {
        manager.removeTasks();
        assertTrue(manager.getTasks().isEmpty(), "Список Задач не очищен");
        manager.removeSubtasks();
        assertTrue(manager.getSubtasks().isEmpty(), "Список Подзадач не очищен");
        manager.removeEpics();
        assertTrue(manager.getEpics().isEmpty(), "Список Эпиков не очищен");
    }

    @Test
    @DisplayName("Проверяет, что поля задач, полученных методами getTask, getSubtask, getEpic соответствуют полям сохраненных задач")
    void shouldReturnEqualFieldsInTask() {
        assertEquals(task.toString(), manager.getTask(1).toString(), "Поля сохраненной  и полученной Задачи не совпадают");
        assertEquals(subtask.toString(), manager.getSubtask(3).toString(), "Поля сохраненной  и полученной Подзадачи не совпадают");
        assertEquals(epic.toString(), manager.getEpic(2).toString(), "Поля сохраненного  и полученного Эпика не совпадают");
    }

    @Test
    @DisplayName("Проверяет, что метод getSubtasksOfEpic возвращает список подзадач эпика, и список не пустой")
    void getSubtasksOfEpic() {
        assertNotNull(manager.getSubtasksOfEpic(2), "Список подзадач эпика не возвращается");
        assertNotEquals(0, manager.getSubtasksOfEpic(2).size(), "Список подзадач не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что последняя задача в списке истории соответствует последней запрошенной задаче")
    void shouldBeEqualInHistory() {
        manager.getTask(1);
        manager.getSubtask(3);
        assertEquals(subtask, manager.getHistory().getLast(), "Неправильная последняя задача в истории");
    }
}