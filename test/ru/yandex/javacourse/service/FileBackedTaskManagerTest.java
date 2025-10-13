package ru.yandex.javacourse.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.javacourse.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileBackedTaskManagerTest {

    File file;

    @BeforeEach
    void createTempFile() {
        try {
            file = File.createTempFile("data", "txt");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании временного файла");
        }
    }

    @Test
    @DisplayName("Проверяет что при загрузке из пустого файла списки задач пустые")
    void loadFromEmptyFileTest() {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        Assertions.assertEquals(0, (manager.getTasks().size() + manager.getEpics().size() + manager.getSubtasks().size()));
    }

    @Test
    @DisplayName("Проверяет что при сохранении в файл пустых списков задач сохраняются только заголовки столбцов")
    void saveEmptyFileTest() {
        String headings = "id,type,name,status,description,epic";
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.save();
        try {
            Assertions.assertEquals(headings, Files.readString(file.toPath()), "При сохранении пустого файла, " +
                    "в нём должны быть названия столбцов - не соответствует");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении временного файла");
        }
    }

    @Test
    @DisplayName("Проверяет что сохраняется и загружается из файла одинаковое количество задач")
    void saveAndLoadQuantityTest() {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        Task task = new Task("title", "description");
        manager.addTask(task);
        Epic epic = new Epic("EpicTitle", "EpicDescription");
        manager.addTask(epic);
        int epicId = epic.getId();
        Subtask subtask = new Subtask("SubtaskTitle", "SubtaskDescription", epicId);
        manager.addTask(subtask);

        FileBackedTaskManager manager1 = new FileBackedTaskManager(file);

        Assertions.assertEquals(3, (manager1.getTasks().size() + manager1.getEpics().size()
                + manager1.getSubtasks().size()), "Различается количество сохранённых и загруженных задач");
    }
}


