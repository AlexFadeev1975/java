package main;

import main.model.ToDoListRepositopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/todolist")
public class ListController {

    @Autowired
    private ToDoListRepositopy toDoListRepositopy;
    List<ToDo> list = new ArrayList<>();

    @PostMapping("/todolist/")

    public int add(String toDo) {
        ToDo newToDo = new ToDo(toDo);
        toDoListRepositopy.save(newToDo);
        return newToDo.getId();
    }

    @PutMapping("/todolist/")
    public void editAll(String toDos) {
        assert (toDos.isEmpty());
        String[] arrToDos = toDos.split("\r\n");
        list.clear();
        deleteAll();
        for (String arrToDo : arrToDos) {
            add(arrToDo);
        }
    }

    @PatchMapping("/todolist/{tasks}")
    public void replace(@PathVariable(name = "tasks") String tasks) {
        String[] arrTasks = tasks.split(",");
        for (ToDo toDo : list) {
            if (toDo.getTask().equals(arrTasks[0])) {
                toDo.setTask(arrTasks[1]);
                toDoListRepositopy.save(toDo);
            }
        }
    }

    @DeleteMapping("/todolist/{task}")
    public void deleteId(@PathVariable(name = "task") String task) {

        for (ToDo toDo : list) {
            if (toDo.getTask().equals(task)) {
                toDoListRepositopy.delete(toDo);
            }
        }
    }

    @GetMapping("/todolist/")
    public List<ToDo> getAll() {
        Iterable<ToDo> toDos = toDoListRepositopy.findAll();
        list.clear();
        for (ToDo toDo : toDos) {
            list.add(toDo);
        }
        return list;
    }

    @DeleteMapping("/todolist/")
    public void deleteAll() {

        toDoListRepositopy.deleteAll();

    }

}
