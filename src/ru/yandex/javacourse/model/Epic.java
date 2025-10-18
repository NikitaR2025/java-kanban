package ru.yandex.javacourse.model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksIds;

    public Epic(String title, String description) {
        super(title, description);
        subtasksIds = new ArrayList<>();
    }

    public Epic(Integer id, String title, TaskStatus status, String description) {
        super(id, title, status, description);
        subtasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    public boolean addSubtaskId(int id) {
        if (id == this.getId()) {
            return false;
        } else {
            subtasksIds.add(id);
            return true;
        }
    }

    public boolean removeSubtaskId(int id) {
        return subtasksIds.remove(Integer.valueOf(id));
    }


    @Override
    public String toString() {
        String result = super.toString() + ", содержит id Subtask: " + subtasksIds.toString();
        return result;
    }


}
