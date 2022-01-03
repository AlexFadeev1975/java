import java.util.*;

public class PhoneBook {

    Map<String, String> phoneBook = new TreeMap<>();
    List<String> allKeySet = new ArrayList<>();

    public void addContact(String phone, String name) {
        // проверьте корректность формата имени и телефона (отдельные методы для проверки)
        // если такой номер уже есть в списке, то перезаписать имя абонента

        if ((phone.matches("^[7,8]{1}[0-9]{10}")) && (name.matches("[а-яА-ЯёЁ]+"))) {
            if (phone.charAt(0) == '8') {
                phone = phone.replaceFirst("8", "7");
            }
            phoneBook.put(phone, name);
        } else {
            System.out.println("Неверный формат ввода");
        }
    }

    public String getContactByPhone(String phone) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найдены - вернуть пустую строку
        if (phoneBook.containsKey(phone)) {
            return phoneBook.get(phone) + " - " + phone;
        } else return "";
    }

    public Set<String> getContactByName(String name) {
        // формат одного контакта "Имя - Телефон"
        // если контакт не найден - вернуть пустой TreeSet
        Set<String> keySet = phoneBook.keySet();


        for (String i : keySet) {
            if (phoneBook.get(i).equals(name)) {
                allKeySet.add(name + " - " + i);
            }

        }
        return new TreeSet<String>(allKeySet);
    }

    public Set<String> getAllContacts() {
        // формат одного контакта "Имя - Телефон"
        // если контактов нет в телефонной книге - вернуть пустой TreeSet
        List<String> contacts = new ArrayList<>();
        List<String> newContacts = new ArrayList<>();
        String addPhoneNumber = "";
        int x = 0;

        for (Map.Entry<String, String> entry : phoneBook.entrySet()) {
            String key = entry.getKey(); // получения ключа
            String value = entry.getValue(); // получения ключа

            contacts.add(value + " - " + key);
        }
        for (int i = contacts.size() - 1; i >= 0; i--) {
            String getContact = contacts.get(i);
            if ((contacts.size() > 1) && (i > 0) && (getContact.substring(0, getContact.indexOf("-")).equals(contacts.get(i - 1).substring(0, contacts.get(i - 1).indexOf("-"))))) {

                addPhoneNumber = ", " + getContact.substring(getContact.indexOf("7"));

            } else {
                String newContact = getContact + addPhoneNumber;
                newContacts.add(x, newContact.trim());
                addPhoneNumber = "";
                x++;

            }
        }

        return new TreeSet<>(newContacts);
    }
}

// для обхода Map используйте получение пары ключ->значение Map.Entry<String,String>
// это поможет вам найти все ключи (key) по значению (value)
    /*
        for (Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey(); // получения ключа
            String value = entry.getValue(); // получения ключа
        }
    */




