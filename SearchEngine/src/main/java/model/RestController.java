package model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestController  {
@RequestMapping (value = "/admin", method = RequestMethod.GET)
public String index () {

return "index";
}
}
