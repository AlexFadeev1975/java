package main;

import main.model.ToDoListRepositopy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class DefaultController {

    @Autowired
    ToDoListRepositopy toDoListRepositopy;

    @RequestMapping("/")
    public String index(Model model) {
        List<ToDo> list = new ArrayList<>();
        toDoListRepositopy.findAll().forEach(list::add);

        model.addAttribute("toDo", list);
        return "index";
    }

}
