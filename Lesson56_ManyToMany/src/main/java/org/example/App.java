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
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            /*1. Добавим новый фильм и добавим в него 2 актёра
            Movie movie = new Movie("Pulp fiction", 1994);
            Actor actor1 = new Actor("Harvey Keitel", 81);
            Actor actor2 = new Actor("Samuel L. Jackson", 72);

            movie.setActors(new ArrayList<>(List.of(actor1, actor2))); //=Arrays.asList()
            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie))); //добавляем связи с двух сторон
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie))); //добавляем связи с двух сторон

            session.save(movie);  //т.к. каскадирование не назначали, то делаем вручную
            session.save(actor1); //т.к. каскадирование не назначали, то делаем вручную
            session.save(actor2); //т.к. каскадирование не назначали, то делаем вручную
            */
            /*2. выведем список актёров для какого-нибудь фильма
            Movie movie = session.get(Movie.class, 3);
            System.out.println(movie.getActors());
            */
            /*3. Добавим новый фильм и свяжем с актёром
            Movie movie = new Movie("Reservoir Dogs", 1992);
            Actor actor = session.get(Actor.class, 5); //Persistence состояние у actor
            actor.getMovies().add(movie); //actor находится в Persistence состоянии, HB это увидит и добавит фильм в БД
            movie.setActors(new ArrayList<>(Collections.singletonList(actor)));
            session.save(movie);
             */
            //4. Удалим фильм у актёра. Удалим связь фильма и актёра
            Actor actor = session.get(Actor.class, 5); //Persistence состояние у actor
            System.out.println(actor.getMovies());

            Movie movieToRemove = actor.getMovies().get(0);
            actor.getMovies().remove(0);        //remove может принимать либо индекс элемента, либо объект. 1 сторона. Удаляем на стороне актёра
            movieToRemove.getActors().remove(actor); //необходимо реализовать хэш-код и equals. 2 сторона. Удаляем на стороне фильма

            session.getTransaction().commit();
        }
    }
}
