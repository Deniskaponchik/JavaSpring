package org.example;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main( String[] args ){
        //import org.hibernate.cfg.Configuration;
        //Конфигурация АВТОМАТИЧЕСКИ подцепила файл конфигурации hibernate.properties из директории resources
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

//            Person person1 = new Person("Mike", 20); //INSERT
//            session.save(person1);  //INSERT

            //Person person = session.get(Person.class, 2); //получаем person с id=2
            // person.setName("New name"); //UPDATE. после commit hibernate обновит данные в БД
            // session.delete(person);

            //обращается не к таблице Person в БД, а к сущности КЛАССУ Person (поле age в классе Person)
            //List<Person> people = session.createQuery("FROM Person where name LIKE 'T%'").getResultList(); //SELECT * FROM Person WHERE
            //for(Person person : people)
                //System.out.println(person);
            //session.createQuery("update Person set name='Test' where age<30").executeUpdate();
            session.createQuery("delete from Person where age<30").executeUpdate();



            session.getTransaction().commit();
            //System.out.println(person.getId()); //получить id Добавленного в БД person

        } finally {
            sessionFactory.close();
        }
    }
}
