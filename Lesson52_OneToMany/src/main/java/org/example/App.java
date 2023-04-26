package org.example;
import org.example.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class App {
    public static void main( String[] args ){
        //import org.hibernate.cfg.Configuration;
        //Конфигурация АВТОМАТИЧЕСКИ подцепила файл конфигурации hibernate.properties из директории resources
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            /*Получаем items  у человека
            Person person = session.get(Person.class, 3);
            List<Item> items = person.getItems();//HB автоматически выполнить SQL запрос к таблице Item. Можно делать только внутри транзакции
            //Получаем person  по item
            Item item = session.get(Item.class, 5);
            Person person = item.getOwner();
            */
            //Создание нового Item из HB
//            Person person = session.get(Person.class, 2);
//            Item newItem = new Item("Item from HB", person);
//            person.getItems().add(newItem); //HB НЕ ВСЕГДА делает запросы к БД. Кэширует. Строчка не порождает SQL-Запросы
//            session.save(newItem); //HB создаст новую строку в тбл Item и вставит правильный id у person

            /*Создаём нового человека + создаём новый item
            Person person = new Person("Test person", 30);
            Item newItem = new Item("Item from HB 2", person);
            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));
            session.save(person); //Пока не настроено каскадирование, нужно делать вручную
            session.save(newItem);
            */
            /*Удаление всех товаров у выбранного person
            Person person = session.get(Person.class, 3);
            List<Item> items = person.getItems();
            for(Item item : items) //пробегаемся по БД
                session.remove(item); //не можем гарантировать, что у человека в Java также синхронизируются автоматом товары из БД
            person.getItems().clear();//НЕ порождает SQL-запрос. удаляем элементы у объекта Java. Синхронизируем кэш HB
            */
            /* Удаление человека
            Person person = session.get(Person.class, 2);
            session.remove(person); //в БД назначили каскадирование. Везде в БД для item проставится null владелец
            person.getItems().forEach(i -> i.setOwner(null)); //Дополнительно в Java для каждого объекта item зануляем владельца.
            //Делаем правильно состояние кэша HB */

            /*Изменим владельца у существующего товара*/
            Person person = session.get(Person.class, 4);
            Item item = session.get(Item.class, 1);
            item.getOwner().getItems().remove(item); //JAVA. Получаем старого владельца.Его список товаров.Удаляем из его списка этот товар
            item.setOwner(person);        //SQL. для этого товара назначаем нового владельца
            person.getItems().add(item);  //JAVA. У нового владельца будет это товар


            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
