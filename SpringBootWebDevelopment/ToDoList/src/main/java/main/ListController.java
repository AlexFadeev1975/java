package main;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController("/todolist")
public class ListController {

    @PostMapping("/todolist/")

    public int add(String toDo) {

        return Storage.add(toDo);
    }

    @PutMapping("/todolist/")
    public void editAll(String toDos) {

        Storage.editAll(toDos);
    }

    @PatchMapping("/todolist/{tasks}")
    public void replace(@PathVariable(name = "tasks") String tasks) {

                 Storage.edit(tasks);

    }

    @DeleteMapping("/todolist/{task}")
    public ResponseEntity deleteId(@PathVariable(name = "task") String task) {

        if (!Storage.toDoList.containsValue(task)) {
            Storage.deleteId(task);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

      @GetMapping("/todolist/")
    public List<ToDo> getAll() {

        return Storage.getList();
    }

   @DeleteMapping("/todolist/")
  public void deleteAll() {

  Storage.deleteAll();
   }

}
