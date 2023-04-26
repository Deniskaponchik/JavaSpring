package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.*;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.util.BookValidator;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;  //понадобится для получения List<Person>
    //private final BookValidator bookValidator;
    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        //this.bookValidator = bookValidator; //в Book ничего не валидируем в этом проекте
    }
    @GetMapping()
    public String index(Model model) {    //СОЗДАЁМ модель
        //получим все книги из DAO (list books), добавим в модель
        model.addAttribute("books", bookDAO.index());  // bookDAO.index() = List<Book>
        //System.out.println(bookDAO.index());
        return "books/index";  //и передадим на отображение в Представление
    }

    @GetMapping("/{id}")
    public String show( @PathVariable("id") int id,
                        Model model     //СОЗДАЁТСЯ НОВАЯ модель
                       ,@ModelAttribute("person")Person person //Создаём пустого Person, чтобы заполнить его на страничке
                    ){

        model.addAttribute("book", bookDAO.show(id));    //Book book = bookDAO.show(id);

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());  // bookOwner.get() = Person owner
        else
            model.addAttribute("people", personDAO.index()); // personDAO.index() = List<Person>

        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,     //СОЗДАЁТСЯ НОВАЯ модель
                       @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));  //Book book = bookDAO.show(id);
        return "books/edit";
    }

    @GetMapping("/new")
    public String newBook(
            @ModelAttribute("book") Book book){ //создаём НОВУЮ ПУСТУЮ книгу, которую передаём в таймлифу для заполнения
        return "books/new";
    }
    /*public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";  */





    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
    //@ModelAttribute = Создание новой книги+Добавление пришедшими с формы значениями сеттерами+Добавление созданного объекта в модель
    BindingResult bindingResult //ДОЛЖЕН идти после валидируемого объекта. Здесь будут лежать ошибки валидации
    ){
        //bookValidator.validate(book, bindingResult);
        //в bindingResult валятся ошибки со всех валидаций

        if(bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);  //Добавление книги в БД. ModelAttribute этим не занимается
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
     //@ModelAttribute = Создание новой книги+Добавление пришедшими с формы ЗНАЧЕНИЯМИ сеттерами+Добавление созданного объекта в модель
                         BindingResult bindingResult,  //должен быть указан сразу после валидируемого объекта
                         @PathVariable("id") int id){

        //bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "/books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person selectedPerson) {//в модели создаём НОВЫЙ объект класса person
                        //@ModelAttribute увидит, что с формы html пришёл ТОЛЬКО id и назначит его объекту selectedPerson
        bookDAO.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }





}





































































