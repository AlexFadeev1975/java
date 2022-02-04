import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (!components[INDEX_NAME].matches("[a-zA-Zа-яА-ЯёЁ]+")) {
            throw new IllegalArgumentException();
        }
        if (!components[INDEX_SURNAME].matches("[a-zA-Zа-яА-ЯёЁ\\-']+")) {
            throw new IllegalArgumentException();
        }
        if (!components[INDEX_PHONE].matches("^[+]?[7,8]{1}[0-9]{10}$")) {
            throw new IllegalArgumentException();
        }
        if (!components[INDEX_EMAIL].matches("[\\w-\\.]+@[\\w-\\.]+[.]\\w{2,3}")) {
            throw new IllegalArgumentException();
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