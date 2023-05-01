package ru.alishev.springcourse.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.models.Mood;
import ru.alishev.springcourse.models.Book;
import ru.alishev.springcourse.models.Person;
import ru.alishev.springcourse.repositories.BooksRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//Мы не можем вызывать методы из репозитория напрямую, потому что мы должны их вызывать внутри HB-транзакции
//Транзакциями в том числе занимается Сервис. в Сервисах содержится Бизнес-логика - различные подсчёты и манипуляции данными.

@Service
@Transactional(readOnly = true)    //import org.springframework.transaction.annotation.Transactional;
//если помечаем @Transactional целый класс, то все ПУБЛИЧНЫЕ методы этого класса будут @Transactional
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {     this.booksRepository = booksRepository;  }

    public List<Book> findAll(boolean sortByYear){    // @Transactional(readOnly = true) по умолчанию для всего класса
        //return booksRepository.findAll(); //ни одного метода в PeopleRepository не реализовали, но у нас есть по умолчанию метод findAll()
        if (sortByYear)
            return booksRepository.findAll(Sort.by("year"));
        else
            return booksRepository.findAll();

        // Пагинация.
        // Метод findAll() в JPA репозитории перегружен и может принимать разные аргументы. В частности, одна из версий этого метода может принимать Pageable.
        // booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
        // С помощью PageRequest.of() указываем номер страницы и количество элементов на одной странице. Нумерация страниц начинается с 0
        // Вызов метода findAll() с указанным Pageable разобьет все существующие объекты в таблице на группы, длиной itemsPerPage каждая,
        // и вернет ту группу, которую вы запрашиваете (page).

        // Пагинация + Сортировка
        // booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();

        //Сортировка
        // List<T> findAll(Sort var1);
        // booksRepository.findAll(Sort.by("НАЗВАНИЕ ПОЛЯ"));  //сортирует возвращаемый список по заданному критерию.
        // Критерий сортировки задаем с помощью Sort.by()
    }
    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        // .getContent - получить всё в виде списка
        if (sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }


    public Book findOne(int id){ //было полезно, что указали, что у нас id это целое число (в interface BooksRepository)
        Optional<Book> foundBook = booksRepository.findById(id); //можем найти Книгу, а можем и не найти
        return foundBook.orElse(null);
    }

    public List<Book> searchByTitle (String query){ //
        return booksRepository.findByTitleStartingWith(query);
    }

    /*Мои упражнения
    public List<Book> findByPersonId(int id){
        List<Book> books = booksRepository.findByPersonId(id);
        Date today = new Date();
        for (Book book : books){
            int diffInDays = (int)((today.getTime() - book.getTakenAt().getTime()) / (1000 * 60 * 60 * 24) );
            if(diffInDays > 10){
                book.setExpired(true);
            }
        }
        return books;
    }*/

    public Person getBookOwner(int id) {
        // Здесь Hibernate.initialize() не нужен, так как владелец (сторона One) загружается не лениво
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);  // Returns null if book has no owner
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void save(Book book){ //СОЗДАНИЕ новой книги
        //person.setMood(Mood.CALM);  //автоматически этот ENUM.CALM будет переведён в индекс 4 при EnumType.ORDINAL
        booksRepository.save(book);   //ничего не нужно реализовывать - за нас уже всё реализовано
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        // добавляем по сути новую книгу (которая не находится в Persistence context), поэтому нужен save()
        updatedBook.setId(id);  //Hb  увидит, что id у книги совпадает с id уже существующей в БД, поэтому обновит, а не сохранит
        //в сущности, которая пришла с формы, поле owner = null (оно там никак не назначается)
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы не терялся владелец при обновлении книги
        booksRepository.save(updatedBook); //приходит книга с формы. Этот объект никак не связан с HB, поэтому метод save для обновления таблицы
    }
    @Transactional //(readOnly=false)    //import org.springframework.transaction.annotation.Transactional;
    public void release(int id){
        //Optional<Book> foundBook = booksRepository.findById(id);
        //foundBook.ifPresent(book -> book.setOwner(null));

        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);    //Сеттеры вызываем на сущности, которая находится в Persistence контексте
            book.setTakenAt(null);  //поэтому HB таблицу автоматически обновит (в отличие от метода update)
        });
    }
    @Transactional //(readOnly=false)    //import org.springframework.transaction.annotation.Transactional;
    public void assign(int id, Person selectedPerson){
        //Optional<Book> foundBook = booksRepository.findById(id);
        //foundBook.ifPresent(book -> book.setOwner(selectedPerson));
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }
    @Transactional //(readOnly=false)    //import org.springframework.transaction.annotation.Transactional;
    public void delete(int id){
        booksRepository.deleteById(id);
    }


    /*не будем создавать для каждого метод в репозитории свой метод в сервисе. А для обучения воспользуемся дебагером
    public void test(){ System.out.println("Testing here with debug. Inside HB Transaction"); //third breakpoint where debugger will stop
    }*/




















}
