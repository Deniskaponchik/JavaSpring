package ru.alishev.springcourse.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.models.Book;

@Component
public class BookValidator implements Validator {
    //Возвращает ошибки, если они будут при ДОБАВЛЕНИИ объектов в БД
    private final BookDAO bookDAO;
    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        /*1. Проверяем, что email в базе нет
        //if(bookDAO.show(book.getEmail()) != null){
        if(bookDAO.show(book.getEmail()).isPresent()){  //лучше, чем проверка на null. Доступен у объектов Optional
            //1.какое поле вызвало ошибку, 2.код ошибки(нас не интересует), 3. code of error
            errors.rejectValue("email", "", "This email is already taken");
            //в rejectValue можно помещать и другие поля, например, name
        } */

        //2. Проверяем, что у книги title начинается с заглавной буквы
        if(!Character.isUpperCase(book.getTitle().codePointAt(0)))
            errors.rejectValue("title", "", "Title should start with a capital letter");

    }
}
