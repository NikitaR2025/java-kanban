import ru.yandex.javacourse.model.*;
import ru.yandex.javacourse.service.*;

public static void main(String[] args) {

    InMemoryTaskManager manager = new InMemoryTaskManager();

    Task task0 = new Task("Уход за котом", "Покормить, выгулять");
    Task task1 = new Task("Заказать еду", "Макароны, гречка, курица");
    manager.addTask(task0);
    manager.addTask(task1);

    Epic epic0 = new Epic("Уборка", "пора наводить порядок");
    manager.addTask(epic0);
    Subtask Epic0subtask0 = manager.createSubtask("Стирка", "шорты, футболка, летняя шапка", "Уборка");
    Subtask Epic0subtask1 = manager.createSubtask("Помыть полы", "Пылесосить, помыть", "Уборка");
    Subtask Epic0subtask2 = manager.createSubtask("Посуда", "Помыть, убрать", "Уборка");
    manager.addTask(Epic0subtask0);
    manager.addTask(Epic0subtask1);
    manager.addTask(Epic0subtask2);

    Epic epic1 = new Epic("Доделать ремонт", "построить стены, поклеить обои");
    manager.addTask(epic1);


}
