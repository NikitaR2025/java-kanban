package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.Task;

import java.util.HashMap;
import java.util.Map;

public class LinkedListBasedOnHashMap {
    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node head = null;
    private Node tail = null;

    public void add(Task task) {
        Node newNode = null;
        if (head == null) {
            newNode = new Node(null, null, task);
            head = newNode;
        } else if (tail == null) {
            newNode = new Node(null, head, task);
            tail = newNode;
            head.setPrevious(tail);
        } else {
            Node oldHead = head;
            newNode = new Node(oldHead, null, task);
            oldHead.setNext(newNode);
            historyMap.put(oldHead.getData().getId(), oldHead);
        }
        historyMap.put(task.getId(), newNode);
    }

    public void remove(Task task) {
        int taskID = task.getId();
        Node nodeToRemove = historyMap.get(taskID);
        Node previous = nodeToRemove.getPrevious();
        Node next = nodeToRemove.getNext();
        previous.setNext(next);
        next.setPrevious(previous);
        historyMap.remove(taskID);


    }

}
