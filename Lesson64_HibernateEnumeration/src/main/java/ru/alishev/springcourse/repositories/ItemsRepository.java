package ru.alishev.springcourse.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.models.*;
import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    //Здесь уже будут методы: finById, findAll, delete, save и т.д.  update не будет

    //названия методов берутся не из головы, а ДОЛЖНЫ соответствовать шаблонам:
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    //https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation


    //Кастомные методы
    //Искать товары по itemName
    List<Item> findByItemName (String itemName); // itemName есть в классе Item

    // Искать товары по владельцу
    List<Item> findByOwner (Person owner); //person.getItems()
    //для выполнения необходимо создать транзакцию. Транзакция создаётся внутри Сервиса




}
