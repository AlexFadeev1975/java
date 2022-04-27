package main;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
    static Integer ID = 1;
    static ConcurrentHashMap<Integer, ToDo> toDoList = new ConcurrentHashMap<>();

    public static int add(String task) {
        int id = ID++;
        ToDo toDo = new ToDo(task);
        toDoList.put(id, toDo);

        return id;
    }

    public static void addAll(List<String> list) {
        list.forEach(Storage::add);

    }

    public static void edit(int id, String task) {
        ToDo newToDo = new ToDo(task);
        toDoList.replace(id, newToDo);
    }

    public static void deleteId(int id) {

        toDoList.remove(id);
    }

    public static List<ToDo> getList() {

        return new ArrayList<>(toDoList.values());
    }

    public static void deleteAll() {

        toDoList.clear();
        ID = 1;
    }

    public static ToDo getId(int id) {

        return toDoList.get(id);
    }
}
