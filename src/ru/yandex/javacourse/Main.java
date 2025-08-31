package ru.yandex.javacourse;

import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        List<Task> history;

        // Создадим задачи, эпики, подзадачи
        Task task0 = new Task("Уход за котом", "Покормить, выгулять");
        Task task1 = new Task("Заказать еду", "Макароны, гречка, курица");
        manager.addTask(task0);
        manager.addTask(task1);

        Epic epic0 = new Epic("Уборка", "пора наводить порядок");
        manager.addTask(epic0);
        Subtask Epic0subtask0 = manager.createSubtask("Стирка", "шорты, футболка, летняя шапка", "Уборка");
        Subtask Epic0subtask1 = manager.createSubtask("Помыть полы", "Пылесосить, помыть", "Уборка");
        Subtask Epic0subtask2 = manager.createSubtask("Посуда", "Помыть, убрать", "Уборка");
        manager.addTask(Epic0subtask0);
        manager.addTask(Epic0subtask1);
        manager.addTask(Epic0subtask2);

        Epic epic1 = new Epic("Доделать ремонт", "построить стены, поклеить обои");
        manager.addTask(epic1);

        // запросим задачи в разном порядке, с повторами, и т.д.
        manager.getTask(task1.getId());
        manager.getTask(task1.getId());
        manager.getSubtask(Epic0subtask2.getId());
        manager.getSubtask(Epic0subtask0.getId());
        manager.getTask(task0.getId());
        manager.getEpic(epic1.getId());
        manager.getTask(task0.getId());
        manager.getEpic(epic1.getId());
        manager.getTask(task0.getId());
        manager.getSubtask(Epic0subtask1.getId());
        manager.getEpic(epic0.getId());
        manager.getEpic(epic0.getId());

        // напечатаем историю просмотров
        history = manager.getHistory();
        printHistory(history);

        System.out.println("Удалим Задачу, title: " + task1.getTitle());
        manager.removeTask(task1.getId());
        history = manager.getHistory();
        printHistory(history);

        System.out.println("Удалим Эпик с тремя подзадачами, его id: " + epic0.getId());
        manager.removeTask(epic0.getId());
        history = manager.getHistory();
        printHistory(history);
    }

    static void printHistory(List<Task> history) {
        for (Task task : history) {
            System.out.println(task);
        }
        System.out.println();
    }

}
