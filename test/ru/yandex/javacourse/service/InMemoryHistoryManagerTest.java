package ru.yandex.javacourse.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    InMemoryHistoryManager historyManager;
    Task firstTask;
    Task secondTask;
    Task thirdTask;


    @BeforeEach
    void setUp() {
        //given
        historyManager = Managers.getDefaultHistory();
        firstTask = new Task("first Title", "first Description");
        secondTask = new Task("second Title", "second Description");
        thirdTask = new Task("third Title", "third Description");
    }

    @Test
    @DisplayName("Проверяет, что метод getHistory возвращает ArrayList")
    void getHistory_shouldReturnNotNullList() {
        //given
        historyManager.linkLast(firstTask);
        //when
        ArrayList<Task> listTask = historyManager.getHistory();
        //then
        assertNotNull(listTask, "Список не вернулся, равен Null");
    }

    @Test
    @DisplayName("Проверяет что первая добавленная в двусвязный список нода становится tail")
    void linkLast_shouldAddFirstNodeToTail() {
        //when
        historyManager.linkLast(firstTask);
        //then
        assertEquals(firstTask.toString(), historyManager.getTail().getData().toString(), "Первая добавленная нода не равна tail");
    }

    @Test
    @DisplayName("Проверяет что вторая добавленная в двусвязный список нода становится head")
    void linkLast_shouldAddSecondNodeToTail() {
        //when
        historyManager.linkLast(firstTask);
        historyManager.linkLast(secondTask);
        //then
        assertEquals(secondTask.toString(), historyManager.getHead().getData().toString(), "Вторая добавленная нода не равна head");
    }

    @Test
    @DisplayName("Проверяет что третья добавленная в двусвязный список нода становится head")
    void linkLast_shouldAddThirdNodeToTail() {
        //when
        historyManager.linkLast(firstTask);
        historyManager.linkLast(secondTask);
        historyManager.linkLast(thirdTask);
        //then
        assertEquals(thirdTask.toString(), historyManager.getHead().getData().toString(), "Третья добавленная нода не равна head");
    }

    @Test
    @DisplayName("Проверяет, что при наличии одной ноды в списке, метод getTasks возвращает ArrayList")
    void getTasks_shouldReturnNotNullList() {
        //when
        historyManager.linkLast(firstTask);
        ArrayList<Task> listTask = historyManager.getTasks();
        //then
        assertNotNull(listTask, "Список не вернулся, равен Null");
    }

    @Test
    @DisplayName("Проверяет, что при наличии трех нод в списке, метод getTasks возвращает ArrayList с тремя элементами")
    void getTasks_shouldReturnRightSize() {
        //when
        historyManager.linkLast(firstTask);
        historyManager.linkLast(secondTask);
        historyManager.linkLast(thirdTask);
        ArrayList<Task> listTask = historyManager.getTasks();
        //then
        assertEquals(3, listTask.size(), "Неправильное количество элементов в ArrayList");
    }

    @Test
    @DisplayName("Проверяет, что при удалении одной ноды, возвращаемый методом getTasks ArrayList имеет правильный size")
    void removeNode_shouldRemoveNode() {
        //given
        historyManager.linkLast(firstTask);
        historyManager.linkLast(secondTask);
        Node nodeToRemove = historyManager.linkLast(thirdTask);
        //when
        historyManager.removeNode(nodeToRemove);
        //then
        assertEquals(2, historyManager.getTasks().size(), "Нода не удалилась из списка");
    }

    @Test
    @DisplayName("Проверяет, что при удалении единственной ноды, возвращаемый методом getTasks ArrayList равен null")
    void removeNode_shouldRemoveSingleNode() {
        //given
        Node nodeToRemove = historyManager.linkLast(firstTask);
        //when
        historyManager.removeNode(nodeToRemove);
        //then
        assertNull(historyManager.getTasks(), "Нода не удалилась из списка");
    }
}




