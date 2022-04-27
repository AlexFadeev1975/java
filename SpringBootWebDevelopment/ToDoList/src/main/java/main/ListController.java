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

    @PostMapping("/todolist/")
    public void addAll(List<String> list) {

        Storage.addAll(list);
    }

    @PatchMapping("/todolist/{id}")
    public ResponseEntity replace(@PathVariable int id, String toDo) {

        if (Storage.toDoList.containsKey(id)) {
            Storage.edit(id, toDo);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity deleteId(@PathVariable int id) {

        if (Storage.toDoList.containsKey(id)) {
            Storage.deleteId(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/todolist/{id}")
    public ResponseEntity getId(@PathVariable int id) {

        if (Storage.toDoList.containsKey(id)) {
            ToDo toDo = Storage.getId(id);
            return new ResponseEntity(toDo, HttpStatus.OK);
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
