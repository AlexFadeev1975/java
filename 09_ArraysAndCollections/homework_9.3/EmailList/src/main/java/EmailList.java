import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.stream.Stream;

public class EmailList {
    public boolean successAdd = true;
    TreeSet<String> emailList = new TreeSet<>();

    public Stream<String> add(String email) {
        // TODO: валидный формат email добавляется
        if (email.matches("\\w+@\\w+[.]\\w{2,3}")) {
            email = email.toLowerCase(Locale.ROOT);
            emailList.add(email);
            successAdd = true;
        } else {
            successAdd = false;
        }

        return emailList.stream();
    }

    public List<String> getSortedEmails() {
        // TODO: возвращается список электронных адресов в алфавитном порядке

        return emailList.stream().toList();
    }

}