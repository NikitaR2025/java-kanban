package ru.yandex.javacourse.service;

import ru.yandex.javacourse.model.HistoryManager;
import ru.yandex.javacourse.model.Node;
import ru.yandex.javacourse.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> history = new HashMap();

    private Node head = null;
    private Node tail = null;

    public Node linkLast(Task task) {
        Node newNode;
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
        return newNode;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (tail != null) {
            tasks.add(tail.getData());
            Node current = tail;
            while (current.getNext() != null) {
                current = current.getNext();
                tasks.add(current.getData());
            }
        }
        return tasks;
    }

    public void removeNode(Node node) {
        if (node == null) {
            return;
        }

        Node previous = node.getPrevious();
        Node next = node.getNext();
        if (previous == null && next == null) { //если список из одного элемента
            tail = null;
        } else if (previous != null && next != null) { //если удаляемый элемент не хвост и не голова
            next.setPrevious(previous);
            previous.setNext(next);
        } else if (previous == null) { //если удаляемый элемент является хвостом
            next.setPrevious(null);
            tail = next;
        } else { //если удаляемый элемент голова
            previous.setNext(null);
            head = previous;
        }

        if (tail == head) {
            head = null;
        }
    }

    @Override
    public void add(Task task) {
        removeNode(history.get(task.getId()));
        history.put(task.getId(), linkLast(task));
    }

    @Override
    public void remove(int id) {
        removeNode(history.get(id));
        history.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    public Node getTail() {
        return tail;
    }

    public Node getHead() {
        return head;
    }

    public int getHistoryMapSize() {
        return history.size();
    }
}
