package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.*;
import ru.alishev.springcourse.services.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService; //понадобится для получения List<Person>

    //private final BookDAO bookDAO;  //BookDAO в данном проекте нет и не используется. Используем JPA
    //private final PersonDAO personDAO;  //понадобится для получения List<Person>
    //private final BookValidator bookValidator;
    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        //this.bookDAO = bookDAO;
        //this.personDAO = personDAO;
        //this.bookValidator = bookValidator; //в Book ничего не валидируем в этом проекте
    }
    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear,
                        Model model) {

        //получим все книги из DAO (list books), добавим в модель
        //model.addAttribute("books", bookDAO.index());  // bookDAO.index() = List<Book>

        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.findAll(sortByYear)); // выдача всех книг
        else
            model.addAttribute("books", booksService.findWithPagination(page, booksPerPage, sortByYear));

        return "books/index";  //и передадим на отображение в Представление
    }

    @GetMapping("/{id}")
    public String show( @PathVariable("id") int id,
                        Model model     //СОЗДАЁТСЯ НОВАЯ модель
                       ,@ModelAttribute("person")Person person //Создаём пустого Person, чтобы заполнить его на страничке
                    ){
        Book book = booksService.findOne(id);
        model.addAttribute("book", book);                        //Book book = booksService.findOne(id);
        Optional<Person> bookOwner = Optional.ofNullable(book.getOwner()); //Можем получить владельца, а может быть null
        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());  // bookOwner.get() = Person owner
        else
            model.addAttribute("people", peopleService.findAll()); // personDAO.index() = List<Person>
        /*
        model.addAttribute("book", bookDAO.show(id));    //Book book = bookDAO.show(id);
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if(bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());  // bookOwner.get() = Person owner
        else
            model.addAttribute("people", personDAO.index()); // personDAO.index() = List<Person>
        */
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,     //СОЗДАЁТСЯ НОВАЯ модель
                       @PathVariable("id") int id) {
        //model.addAttribute("book", bookDAO.show(id));  //Book book = bookDAO.show(id);
        model.addAttribute("book", booksService.findOne(id));  //Book book = bookDAO.show(id);
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


    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }
    @PostMapping("/search")
    public String makeSearch(Model model,
                             @RequestParam("query") String query){
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }


    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
    //@ModelAttribute = Создание новой книги+Добавление пришедшими с формы значениями сеттерами+Добавление созданного объекта в модель
    BindingResult bindingResult //ДОЛЖЕН идти после валидируемого объекта. Здесь будут лежать ошибки валидации
    ){
        //bookValidator.validate(book, bindingResult);
        //в bindingResult валятся ошибки со всех валидаций

        if(bindingResult.hasErrors())
            return "books/new";
        //bookDAO.save(book);  //Добавление книги в БД. ModelAttribute этим не занимается
        booksService.save(book);  //Добавление книги в БД. ModelAttribute этим не занимается
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        //bookDAO.delete(id);
        booksService.delete(id);
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
        //bookDAO.update(id, book);
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        //bookDAO.release(id);
        booksService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person selectedPerson) {//в модели создаём НОВЫЙ объект класса person
                        //@ModelAttribute увидит, что с формы html пришёл ТОЛЬКО id и назначит его объекту selectedPerson
        //bookDAO.assign(id, selectedPerson);
        booksService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }





}





































































