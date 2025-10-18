package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import static ru.yandex.javacourse.model.TaskStatus.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    TaskManager manager;
    Task task;
    int taskId;
    Epic epic;
    int epicId;
    Subtask subtask;
    int subtaskId;
    int listTasksSize;

    @BeforeEach
    @DisplayName("Инициализирует manager, создает task, epic, subtask и добавляет их в manager")
    void setUp() {
        //given
        manager = Managers.getDefault();
        task = new Task("title", "description");
        manager.addTask(task);
        taskId = task.getId();
        epic = new Epic("EpicTitle", "EpicDescription");
        manager.addTask(epic);
        epicId = epic.getId();
        subtask = new Subtask("SubtaskTitle", "SubtaskDescription", epicId);
        manager.addTask(subtask);
        subtaskId = subtask.getId();
    }

    @Test
    @DisplayName("Проверяет что при добавлении Task, Subtask, Epic увеличивается длина соответствующего списка задач")
    void addTask_addTask_shouldIncreaseListTasksLength() {
        //given
        listTasksSize = manager.getTasks().size();
        //when
        manager.addTask(task);
        //then
        assertEquals(++listTasksSize, manager.getTasks().size(), "Не увеличилась длина списка Task");
        //given
        listTasksSize = manager.getSubtasks().size();
        //when
        manager.addTask(subtask);
        //then
        assertEquals(++listTasksSize, manager.getSubtasks().size(), "Не увеличилась длина списка Subtask");
        //given
        listTasksSize = manager.getEpics().size();
        //when
        manager.addTask(epic);
        //then
        assertEquals(++listTasksSize, manager.getEpics().size(), "Не увеличилась длина списка Epics");
    }

    @Test
    @DisplayName("Проверяет, что метод getTasks возвращает список задач, и список не пустой")
    void getTasks_shouldReturnNotEmptyListTasks() {
        //given
        int expectedListTaskSize = 1;
        //when
        ArrayList<Task> listTasks = manager.getTasks();
        //then
        assertNotNull(listTasks, "Список задач не возвращается");
        assertEquals(expectedListTaskSize, listTasks.size(), "Список задач не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что метод getSubtasks возвращает список задач, и список не пустой")
    void getSubtasks_shouldReturnNotEmptyListTasks() {
        //given
        int expectedListTaskSize = 1;
        //when
        ArrayList<Task> listTasks = manager.getSubtasks();
        //then
        assertNotNull(listTasks, "Список Подзадач не возвращается");
        assertEquals(expectedListTaskSize, listTasks.size(), "Список Подзадач не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что метод getEpics возвращает список задач, и список не пустой")
    void getEpics_shouldReturnNotEmptyListTasks() {
        //given
        int expectedListTaskSize = 1;
        //when
        ArrayList<Task> listTasks = manager.getEpics();
        //then
        assertNotNull(listTasks, "Список Эпиков не возвращается");
        assertEquals(expectedListTaskSize, listTasks.size(), "Список Эпиков не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода updateTask количество задач в списке не изменяется")
    void updateTask_ListTasksLengthNotChange() {
        //given
        listTasksSize = manager.getTasks().size();
        task.setDescription("Изменен description");
        //when
        manager.updateTask(task);
        //then
        assertEquals(listTasksSize, manager.getTasks().size(), "Изменилось количество задач");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода removeTask количество задач уменьшилось на 1")
    void removeTask_ShouldDecreaseListTasksLength() {
        //given
        listTasksSize = manager.getTasks().size();
        //when
        manager.removeTask(1);
        //then
        assertEquals(--listTasksSize, manager.getTasks().size(), "Не уменьшилось количество задач");
    }

    @Test
    @DisplayName("Проверяет, что при вызове метода removeTasks все списки задач становятся пустыми")
    void removeTasks_ListsTasksShouldBeEmpty() {
        //when
        manager.removeTasks();
        //then
        assertTrue(manager.getTasks().isEmpty(), "Список Задач не очищен");
        //when
        manager.removeSubtasks();
        //then
        assertTrue(manager.getSubtasks().isEmpty(), "Список Подзадач не очищен");
        //when
        manager.removeEpics();
        //then
        assertTrue(manager.getEpics().isEmpty(), "Список Эпиков не очищен");
    }

    @Test
    @DisplayName("Проверяет, что поля задач, полученных методами getTask, getSubtask, getEpic соответствуют полям сохраненных задач")
    void getTask_ReceivedFieldsShouldBeEqualSavedFields() {
        //when
        Task task1 = manager.getTask(taskId);
        //then
        assertEquals(task.toString(), task1.toString(), "Поля сохраненной  и полученной Task не совпадают");
        //when
        Epic epic1 = manager.getEpic(epicId);
        //then
        assertEquals(epic.toString(), epic1.toString(), "Поля сохраненного  и полученного Epic не совпадают");
        //when
        Subtask subtask1 = manager.getSubtask(subtaskId);
        //then
        assertEquals(subtask.toString(), subtask1.toString(), "Поля сохраненной  и полученной Subtask не совпадают");
    }

    @Test
    @DisplayName("Проверяет, что метод getSubtasksOfEpic возвращает список подзадач эпика, и список не пустой")
    void getSubtasksOfEpic_shouldReturnNotEmptyList() {
        //when
        ArrayList<Subtask> subtasks = manager.getSubtasksOfEpic(2);
        //then
        assertNotNull(subtasks, "Список подзадач не возвращается (равен Null)");
        assertNotEquals(0, subtasks.size(), "Список подзадач не должен быть пуст");
    }

    @Test
    @DisplayName("Проверяет, что последняя задача в списке истории соответствует последней запрошенной по id задаче")
    void getTask_shouldAddTaskInEndHistory() {
        //given
        int idFirst = 1;
        int idSecond = 3;
        //when
        manager.getTask(idFirst);
        Subtask subtaskSecond = manager.getSubtask(idSecond);
        //then
        assertEquals(subtaskSecond, manager.getHistory().getLast(), "Неправильная последняя задача в истории");
    }

    @Test
    @DisplayName("Проверяет, что метод getHistory возвращает List")
    void getHistory_shouldReturnList() {
        //given
        manager.getTask(taskId);
        //when
        List<Task> listHistory = manager.getHistory();
        //then
        assertNotNull(listHistory, "метод getHistory не вернул List");
    }

    @Test
    @DisplayName("Проверяет что при установке статуса Subtask DONE метод checkStatusEpic изменяет статус Epic DONE")
    void checkStatusEpic_subtaskSetStatusDONE_shouldBeEpicStatusDONE() {
        //given
        subtask.setStatus(DONE);
        //when
        manager.checkStatusEpic(epicId);
        //then
        assertEquals(DONE, manager.getEpic(epicId).getStatus(), "Статус Epic не DONE");
    }
}