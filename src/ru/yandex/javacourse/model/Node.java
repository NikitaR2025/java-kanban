package ru.yandex.javacourse.model;

public class Node {
    private Node previous;
    private Node next;
    private final Task data;

    public Node(Node previous, Node next, Task data) {
        this.previous = previous;
        this.next = next;
        this.data = data;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Task getData() {
        return data;
    }
}
