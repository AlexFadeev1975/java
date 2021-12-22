import java.util.Scanner;

public class Main {
    private static TodoList todoList = new TodoList();

    public static void main(String[] args) {
        // TODO: написать консольное приложение для работы со списком дел todoList
        TodoList list = new TodoList();
        System.out.println("ADD -    Добавить дело в список" + "\r\n" +
                "EDIT -   Редактировать список" + "\r\n" +
                "DELETE - Удалить дело из списка" + "\r\n" +
                "LIST -   Печать списка");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            String regexD = "LIST||ADD||EDIT||DELETE";

            if (choice.matches(regexD)) {
                switch (choice) {
                    case ("ADD"): {
                        System.out.print("Введите дело: ");
                        String todo = scanner.nextLine();
                        String todos = todo.trim();
                        if (todos.matches("(\\d)-[\\w]+")) {
                            list.add((Character.digit(todos.charAt(0), 10) - 1), todos.substring(2));
                            break;
                        } else {
                            list.add(todos);
                            break;
                        }
                    }
                    case ("EDIT"): {
                        System.out.print("Введите дело в новой редакции: ");
                        String todo = scanner.nextLine();
                        if (!(todo.matches("(\\d)-[\\w]+"))) {
                            System.out.println("Не указан номер дела");
                        } else {
                            list.edit(todo.substring(2), (Character.digit(todo.charAt(0), 10) - 1));
                        }
                        break;
                    }
                    case ("DELETE"): {
                        System.out.print("Введите дело для удаления: ");
                        String todo = scanner.nextLine();
                        if ((Integer.parseInt(todo) < list.getTodos().size())) {
                            list.delete((Integer.parseInt(todo) - 1));
                        } else {
                            System.out.println("Дело с таким номером не существует");
                        }
                        break;
                    }
                    case ("LIST"): {
                        for (int i = 0; i < list.getTodos().size(); i++) {

                            System.out.println((i + 1) + " - " + list.getTodos().get(i));

                        }
                    }
                }
            }
        }
    }
}