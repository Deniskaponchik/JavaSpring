package ru.alishev.springcourse.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/calculator")
    public String calculator(@RequestParam(value = "a", required = true) int a,
                            @RequestParam(value = "b", required = true) int b,
                            @RequestParam(value = "action", required = false) String action,
                            Model model
    ){
        double result;
        switch(action){
            case "multiplication":
                result = a * b;
                break;
            case "division":
                result = a / (double)b;
                break;
            case "subtraction":
                result = a - b;
                break;
            case "addition":
                result = a + b;
                break;
            default:
                result = 0;
                break;
        }
        model.addAttribute("result", result);
        return"first/calculator";  //возращается HTML страница calculator.html
        /*
        if(action.equals("multiplication")){
            //model.addAttribute("message", "multiplication = " + a * b);
            model.addAttribute("message", a + "*" + b + "=" + (a * b));
        }
        else if("addition".equals(action)){
            model.addAttribute("message", "addition = " + (a + b));
        }
        else if(action.equals("subtraction")){
            model.addAttribute("message", "subtraction = " + (a - b));
        }
        else if(action.equals("division")){
            model.addAttribute("message", "division = " + (a / b));
        }
        return "first/calculator";
         */
    }
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname,
                            Model model
                            ){
        model.addAttribute("message", "Hello, " + name + " " + surname);
        return "first/hello";
    }
    @GetMapping("/goodbye")
    public String goodbyePage() {
        return "first/goodbye";
    }
}
