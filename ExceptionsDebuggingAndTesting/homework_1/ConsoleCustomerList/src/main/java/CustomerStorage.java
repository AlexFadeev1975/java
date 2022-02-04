import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private static final int INDEX_NAME = 0, INDEX_SURNAME = 1, INDEX_EMAIL = 2, INDEX_PHONE = 3;
    private static final String REGEX_NAME = "[a-zA-Zа-яА-ЯёЁ]+";
    private static final String REGEX_SURNAME = "[a-zA-Zа-яА-ЯёЁ\\-']+";
    private static final String REGEX_PHONE = "[+]?[7,8]{1}[0-9]{10}";
    private static final String REGEX_EMAIL = "[\\w-\\.]+@[\\w-\\.]+[.]\\w{2,3}";
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {


        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalArgumentException("Неверный формат данных");
        }
        if (!components[INDEX_NAME].matches(REGEX_NAME)) {
            throw new IllegalArgumentException("Неверный формат имени. Поле должно содержать хотя бы одну букву.");
        }
        if (!components[INDEX_SURNAME].matches(REGEX_SURNAME)) {
            throw new IllegalArgumentException("Неверный формат фамилии.Поле должно содержать хотя бы одну букву.");
        }
        if (!components[INDEX_PHONE].matches(REGEX_PHONE)) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Номер телефона состоит из 11 цифр.");
        }
        if (!components[INDEX_EMAIL].matches(REGEX_EMAIL)) {
            throw new IllegalArgumentException("Неверный формат Email. Формат Email: 'alex@ya.ru'");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];

        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));

    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        try {
            if (!storage.containsKey(name)) {
                throw new IllegalArgumentException("Такого имени не существует");
            }
            storage.remove(name);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}