package ru.alishev.springcourse.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        // return jdbcTemplate.query("SELECT * FROM Book", new BookMaper()); //свой BookMaper()
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
        //для поля из БД person_id попытается вызвать метод класса setPersonId. Не найдёт его, ошибок не будет, продолжит работу
    }
    public Book show(int id) {
        // Если указываем знак ? то jdbcTemplate понимает/ждёт PreparedStatement что мы в ? укажем значения
        //return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id}, new BookMaper())
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
        //если в строке будет найден объект, то будет возвращён он. в отличном случае null (лучше Error в реальном приложении)
        //в БД row name может содержать знак _  - он удалится и трансформируется в поле класса без знака _
        //Порядок расположения полей в БД не важен
        //
    }
    public void save(Book book) {
        //jdbcTemplate понимает, что PreparedStatement и дальше ждёт значение аргументов
        jdbcTemplate.update("INSERT INTO Book(title, year, author) VALUES(?, ?, ?)",
                book.getTitle(), book.getYear(), book.getAuthor());
    }
    public void update(int id, Book updatedBook) {
        //синтаксис с vararg
        jdbcTemplate.update("UPDATE Book SET title=?, year=?, author=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getYear(), updatedBook.getAuthor(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public Optional<Person> getBookOwner(int id) { //необходим в BookController.show
        // Выбираем все колонки таблицы Person из объединенной таблицы (Person.*)
        // Join'им таблицы Book и Person и получаем человека, которому принадлежит книга с указанным id
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id " +
                            "WHERE Book.id = ?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = NULL WHERE id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?", selectedPerson.getId(), id);
    }



    /*
    public Optional<Book> show(String email){
        //Optional -класс обёртка. В данном случае избавляет от проверки на null итоговый объект.
        //Мы предполагаем, что можем вернуться объект book, а может и не вернуться
        //передаём email в качестве аргумента в PreparedStatement
        return jdbcTemplate.query("SELECT * FROM Book WHERE email = ?", new Object[]{email},
                //мапим resultset, который пришёл от базы данных, в человека
                //new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null); //без Optional
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public List<Person> allPeople() {
        // return jdbcTemplate.query("SELECT * FROM Person", new PersonMaper()); //свой PersonMaper()
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));   // Предустановленный BeanPropertyRowMapper<>
        //Делает из полученного запроса лист бинов класса Person
    }*/
    
}