package ru.alishev.springcourse.Project2Boot.services;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.Project2Boot.models.Person;
import ru.alishev.springcourse.Project2Boot.models.*;
import ru.alishev.springcourse.Project2Boot.repositories.PeopleRepository;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//Мы не можем вызывать методы из репозитория напрямую, потому что мы должны их вызывать внутри HB-транзакции
//Транзакциями в том числе занимается Сервис
//Также в Сервисах содержится Бизнес-логика - различные подсчёты и манипуляции данными.

@Service
@Transactional(readOnly = true)  //import org.springframework.transaction.annotation.Transactional;
//если помечаем @Transactional целый класс, то все ПУБЛИЧНЫЕ методы этого класса будут @Transactional
public class PeopleService {
    private final PeopleRepository peopleRepository;
    //private final BooksRepository booksRepository; //у Алишева без него

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {        this.peopleRepository = peopleRepository;    }

    public List<Person> findAll(){         // @Transactional(readOnly = true) по умолчанию для всего класса
        return peopleRepository.findAll(); //ни одного метода в PeopleRepository не реализовали, но у нас есть по умолчанию метод findAll()

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

    public Person findOne(int id){ //было полезно, что указали, что у нас id это целое число (в interface PeopleRepository)
        Optional<Person> foundPerson = peopleRepository.findById(id); //можем найти человека, а можем и не найти
        return foundPerson.orElse(null); //если человек есть, то возвращаем его, если человека нет, то возвращаем null
    }

    public Optional<Person> getPersonByFullName(String fullName){
        return peopleRepository.findByFullName(fullName);
    }

    public List<Book> getBooksByPersonId(int id) { //используется на странице person/show при выведении книг
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            // Мы внизу итерируемся по книгам, поэтому они точно будут загружены, но на всякий случай
            // не мешает всегда вызывать Hibernate.initialize()
            // (на случай, например, если код в дальнейшем поменяется и итерация по книгам удалится)

            person.get().getBooks().forEach(book -> {  // Проверка просроченности книг
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                // Math.abs - чтобы значение в мс было АБСОЛЮТНОЕ. Без него получали бы ОТРИЦАТЕЛЬНОЕ число
                if (diffInMillies > 864000000) // 864000000 милисекунд = 10 суток
                    book.setExpired(true); // книга просрочена
            });
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList(); //возвращаем пустой список
        }
    }

    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void save(Person person){
        //person.setMood(Mood.CALM);     //автоматически этот ENUM.CALm будет переведён в индекс 4 при EnumType.ORDINAL
        peopleRepository.save(person); //ничего не нужно реализовывать - за нас уже всё реализовано в Репозитории
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson); //в репозитории есть соглашение, что для ДОБАВЛЕНИЯ сущности и ОБНОВЛЕНИЯ сущности используется один и тот же метод
        //метод save увидит, что передаётся человек с id, который уже есть в тбл. и обновит значения у существуещего человека
    }


    @Transactional //(readOnly=false) //import org.springframework.transaction.annotation.Transactional;
    public void delete(int id){
        peopleRepository.deleteById(id);
    }



    /*не будем создавать для каждого метод в репозитории свой метод в сервисе. А для обучения воспользуемся дебагером
    public void test(){
        System.out.println("Testing here with debug. Inside HB Transaction"); //third breakpoint where debugger will stop
    }*/
}
