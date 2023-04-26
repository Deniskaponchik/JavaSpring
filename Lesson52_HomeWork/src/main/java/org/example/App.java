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
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            /*2. получите любого режиссера, а затем получите список его фильмов.
            Director director = session.get(Director.class, 3); //HQL. director c id 3.
            List<Movie> movies = director.getMovies();//HQL. HB автоматически выполнит SQL запрос к тбл Movie.
            //getMovies = будет отправлять SQL запрос ТОЛЬКО внутри транзакции. Вне транзакции будет работать, как обычный геттер
            */
            /*3. Получите любой фильм, а затем получите его режиссера.
            Movie movie = session.get(Movie.class, 5); //HQL.
            System.out.println(movie);
            Director director = movie.getOwner();      //АЛИШЕВ: Здесь HB делает SQL запрос. Это НЕ JAVA
            */
            /*4.Добавьте еще один фильм для любого режиссера. Создание нового Movie из HB
            Director director = session.get(Director.class, 2); //HQL
            Movie newMovie = new Movie("Movie from HB", director); //JAVA
            director.getMovies().add(newMovie); //JAVA. HB НЕ ВСЕГДА делает запросы к БД. Кэширует. Строчка не порождает SQL-Запросы
            session.save(newMovie);             //HQL. HB создаст новую строку в тбл Item и вставит правильный id у director
            */
            /*5.Создайте нового режиссера и новый фильм и свяжите эти сущности.
            Director director = new Director("Test director", 30); //JAVA
            Movie newMovie = new Movie("Movie from HB 2", director);   //JAVA
            director.setMovies(new ArrayList<>(Collections.singletonList(newMovie))); //JAVA
            session.save(director); //HQL. Пока не настроено каскадирование, нужно делать вручную
            session.save(newMovie); //HQL.
            */
            /*6.Смените режиссера у существующего фильма
            Director director = session.get(Director.class, 4); //будущий режиссёр
            Movie movie = session.get(Movie.class, 1);
            movie.getOwner().getMovies().remove(movie);//JAVA.Получаем старого владельца.Его список фильмов.Удаляем из его списка этот фильм
            movie.setOwner(director);         //SQL. для этого фильма назначаем нового режиссёра
            director.getMovies().add(movie);  //JAVA. У нового владельца будет это товар
            */
            //7.Удалите фильм у любого режиссера (занулить режиссёра у фильма НЕЛЬЗЯ)
            Director director = session.get(Director.class, 1);
            //director.getMovies().stream().forEach(m -> System.out.println(m.getName()));
            List<Movie> movies = director.getMovies();
            for(Movie movie : movies)  //пробегаемся по БД
                if(movie.getName().equals("Reservoir Dogs"))
                    session.remove(movie); //не можем гарантировать, что у director в Java также синхронизируются автоматом фильмы из БД
            director.getMovies().removeIf(m -> m.getName().equals("Reservoir Dogs")); //пробегаемся по Листу

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



            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}
