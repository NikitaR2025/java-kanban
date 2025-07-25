package ru.yandex.javacourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    @DisplayName("Проверяет что невозможно установить свой id в своё поле epicId")
    void setEpicId_() {
        //given
        Subtask subtask = new Subtask("title", "description", 0);
        subtask.setId(1);
        //when
        boolean makeSubtaskOwnEpic = subtask.setEpicId(1);
        //then
        assertFalse(makeSubtaskOwnEpic, "Subtask не должен быть собственным Эпиком");
    }
}