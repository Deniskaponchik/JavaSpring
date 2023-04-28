package ru.alishev.springcourse.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.repositories.ItemsRepository;
import ru.alishev.springcourse.models.*;
import java.util.List;

//Мы не можем вызывать методы из репозитория напрямую, потому что мы должны их вызывать внутри HB-транзакции
//Транзакциями в том числе занимается Сервис
//Также в Сервисах содержится Бизнес-логика - различные подсчёты и манипуляции данными.

@Service
@Transactional  //import org.springframework.transaction.annotation.Transactional;
public class ItemsService {

    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }


    public List<Item> findByItemName(String itemName){
        return itemsRepository.findByItemName(itemName); //first breakpoint where debugger will stop
    }


    public List<Item> findByOwner(Person owner){
        return itemsRepository.findByOwner(owner);  //second breakpoint where debugger will stop
    }


}
