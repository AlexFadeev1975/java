package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public static void editAll(String task) {
        String[] toDos = task.split("\r\n");
        toDoList.clear();
        for (int i = 0; i < toDos.length; i++) {
            add(toDos[i]);
        }
    }

    public static void edit(String tasks) {
        int id = 0;
        String[] arrTasks = tasks.split(",");
        Set<Map.Entry<Integer, ToDo>> set = toDoList.entrySet();
        for (Map.Entry<Integer, ToDo> item : set) {
            if (item.getValue().task.equals(arrTasks[0])) {
                id = item.getKey();
            }
        }
        ToDo newToDo = new ToDo(arrTasks[1]);
        toDoList.replace(id, newToDo);
    }

    public static void deleteId(String task) {

        Set<Map.Entry<Integer, ToDo>> set = toDoList.entrySet();
        for (Map.Entry<Integer, ToDo> item : set) {
            if (item.getValue().task.equals(task)) {
                int id = item.getKey();
                toDoList.remove(id);
            }
        }

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
