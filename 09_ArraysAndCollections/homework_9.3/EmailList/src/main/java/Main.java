import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static final String WRONG_EMAIL_ANSWER = "Неверный формат email";


    /* TODO:
        Пример вывода списка Email, после ввода команды LIST в консоль:
        test@test.com
        hello@mail.ru
        - каждый адрес с новой сAтроки
        - список должен быть отсортирован по алфавиту
        - email в разных регистрах считается одинаковыми
           hello@skillbox.ru == HeLLO@SKILLbox.RU
        - вывод на печать должен быть в нижнем регистре
           hello@skillbox.ru
        Пример вывода сообщения об ошибке при неверном формате Email:
        "Неверный формат email"
    */


    public static void main(String[] args) {
        EmailList list = new EmailList();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            if (input.matches("([ADLIST]{3,4}\\s\\w+@\\w+[.]\\w{2})|([LIST]+)")) {
                String commandAdd = input.substring(0, 3);
                String commandList = input.substring(0, 4);

                if (commandAdd.matches("^[ADD]+")) {

                    String mail = input.substring(4);
                    mail = mail.trim();
                    list.add(mail.toLowerCase(Locale.ROOT));
                }
                if (commandList.matches("^[LIST]+")) {

                    list.getSortedEmails().forEach(elem -> System.out.println(elem));
                }
            } else {
                System.out.println(WRONG_EMAIL_ANSWER);
            }

        }
    }
}







