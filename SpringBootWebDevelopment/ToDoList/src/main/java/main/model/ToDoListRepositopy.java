package main.model;

import main.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoListRepositopy extends CrudRepository<ToDo, Integer> {

}
