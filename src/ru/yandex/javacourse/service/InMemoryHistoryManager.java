package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.LinkedListBasedOnHashMap;
import ru.yandex.javacourse.model.Node;
import ru.yandex.javacourse.model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedListBasedOnHashMap history = new LinkedListBasedOnHashMap();


    private Node head = null;
    private Node tail = null;

    public void linkLast(Task task) {
        Node newNode = null;
        if (tail == null) {
            newNode = new Node(null, null, task);
            tail = newNode;
        } else if (head == null) {
            newNode = new Node(tail, null, task);
            head = newNode;
            tail.setNext(head);
            //historyMap.put(tail.getData().getId(), tail);
        } else {
            Node oldHead = head;
            newNode = new Node(oldHead, null, task);
            oldHead.setNext(newNode);
            //historyMap.put(oldHead.getData().getId(), oldHead);
            head = newNode;
        }
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> Tasks = new ArrayList<>();
        if (head == null && tail == null) {
            return null;
        } else {
            Tasks.add(tail.getData());
            Node current = tail;
            while (current.getNext() != null) {
                current = current.getNext();
                Tasks.add(current.getData());
            }
        }
        return Tasks;
    }

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

    public Node getTail() {
        return tail;
    }

    public Node getHead() {
        return head;
    }
}
