import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

public class EmailList {

    TreeSet<String> emailList = new TreeSet<>();

    public Stream<String> add(String email) {
        // TODO: валидный формат email добавляется
        emailList.add(email);

        return emailList.stream();
    }

    public List<String> getSortedEmails() {
        // TODO: возвращается список электронных адресов в алфавитном порядке

        return emailList.stream().toList();
    }

}