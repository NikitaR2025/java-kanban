public class Main {

    // Тестирование:

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();
        Task task = new Task("Уход за котом", "Покормить, выгулять");
        Task task2 = new Task("Заказать еду", "Макароны, гречка, курица");
        manager.addTask(task);
        manager.addTask(task2);

        Epic epic = new Epic("Уборка", "пора наводить порядок");
        manager.addTask(epic);
        Subtask subtask = manager.createSubtask("Стирка", "шорты, футболка, летняя шапка", "Уборка");
        Subtask subtask2 = manager.createSubtask("Помыть полы", "Пылесосить, помыть", "Уборка");
        manager.addTask(subtask);
        manager.addTask(subtask2);
        subtask.setStatus(TaskStatus.DONE);
        manager.updateTask(subtask);
        subtask2.setStatus(TaskStatus.DONE);
        manager.updateTask(subtask2);

        Epic epic2 = new Epic("Доделать 4 спринт Практикум", "срочно-срочно");
        manager.addTask(epic2);
        Subtask subtask1 = manager.createSubtask("Финальное задание",
                "Протестировать, и отправить на проверку", "Доделать 4 спринт Практикум");
        manager.addTask(subtask1);
        subtask1.setDescription("Отправить на проверку");
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(subtask1);


        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        System.out.println(manager.getSubtasksOfEpic(6));

    }
}
