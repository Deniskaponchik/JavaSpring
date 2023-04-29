package ru.alishev.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.Person;
import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> { //Integer - Тип первичного ключа у класса
    //названия методов берутся не из головы, а ДОЛЖНЫ соответствовать шаблонам:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    //Здесь уже будут методы: finById, findAll, delete, save и т.д.  update не будет

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




    //Кастомные методы
    //Найдём людей по имени
    List<Person> findByName(String name);


    // Найдём людей по имени и отсортируем по возрасту
    List<Person> findByNameOrderByAge (String name);

    //
    List<Person> findByEmail (String email);

    //Поиск людей, у которых имена начинаются со строки startingWith
    List<Person> findByNameStartingWith (String startingWith);


    List<Person> findByNameOrEmail (String name, String email); //передать в метод нужно И имя И email, но найдёт ИЛИ


}
