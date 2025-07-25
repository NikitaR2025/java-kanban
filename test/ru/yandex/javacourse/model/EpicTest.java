package ru.yandex.javacourse.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    @DisplayName("Проверяет что Эпик невозможно добавить в свой список Subtask")
    void addSubtaskId_should_NotAllowEpic_ToContainItself() {
        //given
        Epic epic = new Epic("title", "description");
        epic.setId(1);
        //when
        boolean addEpicToItself = epic.addSubtaskId(1);
        //then
        assertFalse(addEpicToItself, "Эпик добавлен в свой список Subtask");
    }

}