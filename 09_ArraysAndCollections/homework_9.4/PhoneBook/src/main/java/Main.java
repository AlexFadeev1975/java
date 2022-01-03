import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        PhoneBook phoneBook = new PhoneBook();

        while (true) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите номер, имя или команду");
            String input = scanner.nextLine();

            if (input.matches("[0-9]{11}")) {

                String name = phoneBook.getContactByPhone(input);
                if (!(name.equals(""))) {
                    System.out.println(name + " - " + input);
                } else {
                    System.out.println("Такого номера нет в телефонной книге." + "\r\n" +
                            "Введите имя абонента для номера " + "\"" + input + "\"");
                    String inputName = scanner.nextLine();
                    System.out.println(inputName);
                    phoneBook.addContact(input, inputName);
                    System.out.println("Контакт сохранен!");
                    continue;
                }
            }
            if (input.matches("[а-яА-ЯёЁ]+")) {

                if (phoneBook.getContactByName(input).isEmpty()) {
                    System.out.println("Такого имени в телефонной книге нет." + "\r\n" +
                            "Введите номер телефона для абонента " + "\"" + input + "\"");
                    String inputPhone = scanner.nextLine();
                    phoneBook.addContact(inputPhone, input);
                    continue;
                } else
                    System.out.println(phoneBook.getContactByName(input).toString());
                continue;
            }
            if (input.matches("LIST")) {
                phoneBook.getAllContacts().forEach(System.out::println);


            } else {
                System.out.println("Неверный формат ввода!");
            }

        }
    }
}
