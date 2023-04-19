package ru.alishev.springcourse.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.util.BookValidator;
import javax.validation.Valid;
@Controller
@RequestMapping("/books")
public class BooksController {
    @GetMapping()
    public String index(Model model) {    //СОЗДАЁМ модель
        //получим все книги из DAO (list books), добавим в модель
        model.addAttribute("books", bookDAO.index());  // bookDAO.index() = List<Book>
        return "books/index";  //и передадим на отображение в Представление
    } // +++++
    @GetMapping("/new")
    public String newBook(
            @ModelAttribute("book") Book book){ //создаём НОВУЮ ПУСТУЮ модель, которую передаём в Представление таймлифу
        return "books/new";
    } //+++++
    /*public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";  */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model){    //СОЗДАЁТСЯ НОВАЯ модель
        //получим 1 человека (объект Book) по его id из DAO
        model.addAttribute("book", bookDAO.show(id));   //Book book = bookDAO.show(id);
        model.addAttribute("personBook", bookDAO.)
        return "books/show";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,     //СОЗДАЁТСЯ НОВАЯ модель
                       @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));  //Book book = bookDAO.show(id);
        return "books/edit";
    } //+++++




    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
    //@ModelAttribute = Создание нового человека+Добавление значений сеттерами+Добавление созданного объекта в модель
                 BindingResult bindingResult //ДОЛЖЕН идти после валидируемого объекта. Здесь будут лежать ошибки валидации
    ){
        bookValidator.validate(book, bindingResult);
        //в bindingResult валятся ошибки со всех валидаций

        if(bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);  //Добавление книги в БД. ModelAttribute этим не занимается
        return "redirect:/books";
    } //+++++
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, //получаем со страницы редактирования заполненную модель
                         BindingResult bindingResult,  //должен быть указан сразу после валидируемого объекта
                         @PathVariable("id") int id){

        bookValidator.validate(book, bindingResult);

        if(bindingResult.hasErrors())
            return "/books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    } //+++++
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }






    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
    }

}
