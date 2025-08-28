package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedListBasedOnHashMap history = new LinkedListBasedOnHashMap();

    @Override
    public void add(Task task) {
        history.add(task);
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> historyList = new ArrayList<>();
        Node head = history.getHead();
        Node tail = history.getTail();
        if (head == null && tail == null) {
            return null;
        } else if (tail == null) {
            historyList.add(head.getData());
        } else {
            historyList.add(tail.getData());
            Node current = tail;
            while (current.getNext() != null) {
                current = current.getNext();
                historyList.add(current.getData());
            }
        }


        return historyList;
    }
}
