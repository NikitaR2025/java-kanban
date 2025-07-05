package ru.yandex.javacourse.model;

import java.util.Objects;

public class Task {

    private Integer id;
    private String title;
    private String description;
    private TaskStatus status;

    public Task(String title, String description) {
        this.id = 0;
        this.title = title;
        this.description = description;
        status = TaskStatus.NEW;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getClass() + ", " + title + ": " + description + ". Статус: " + status;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

