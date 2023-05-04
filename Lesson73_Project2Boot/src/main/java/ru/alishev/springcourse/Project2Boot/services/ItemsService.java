package ru.alishev.springcourse.Project2Boot.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.Project2Boot.models.*;
import ru.alishev.springcourse.Project2Boot.repositories.ItemsRepository;
import java.util.List;

//Мы не можем вызывать методы из репозитория напрямую, потому что мы должны их вызывать внутри HB-транзакции
//Транзакциями в том числе занимается Сервис. в Сервисах содержится Бизнес-логика - различные подсчёты и манипуляции данными.

@Service
@Transactional  //import org.springframework.transaction.annotation.Transactional;
//если помечаем @Transactional целый класс, то все ПУБЛИЧНЫЕ методы этого класса будут @Transactional
public class ItemsService {
    private final ItemsRepository itemsRepository;
    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

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


    public List<Item> findByItemName(String itemName){
        return itemsRepository.findByItemName(itemName); //first breakpoint where debugger will stop
    }


    public List<Item> findByOwner(Person owner){
        return itemsRepository.findByOwner(owner);  //second breakpoint where debugger will stop
    }


}
