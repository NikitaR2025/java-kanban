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
        if (tail == null) {
            newNode = new Node(null, null, task);
            tail = newNode;
        } else if (head == null) {
            newNode = new Node(tail, null, task);
            head = newNode;
            tail.setNext(head);
            historyMap.put(tail.getData().getId(), tail);
        } else {
            Node oldHead = head;
            newNode = new Node(oldHead, null, task);
            oldHead.setNext(newNode);
            historyMap.put(oldHead.getData().getId(), oldHead);
            head = newNode;
        }
        historyMap.put(task.getId(), newNode);
    }

    public void remove(int id) {
        Node nodeToRemove = historyMap.get(id);
        Node previous = nodeToRemove.getPrevious();
        Node next = nodeToRemove.getNext();
        previous.setNext(next);
        next.setPrevious(previous);
        historyMap.remove(id);
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }
}
