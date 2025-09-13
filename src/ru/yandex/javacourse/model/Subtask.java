package ru.yandex.javacourse.model;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public boolean setEpicId(int epicId) {
        if (this.getId() == epicId) {
            return false;
        } else {
            this.epicId = epicId;
            return true;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Принадлежит эпику id: " + epicId;
    }
}
