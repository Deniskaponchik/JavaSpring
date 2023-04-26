package org.example.model;
import javax.persistence.*;

@Entity
@Table(name="Movie")
public class Movie {
    @Id
    @Column(name="movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //указываем Hibernate не трогать эту колонку. Всё на стороне postgres
    //@GeneratedValue(strategy = GenerationType.SEQUANCE,
    // generator="seq_generator_person")   //
    //SequenceGenerator(name="seq_generator_person",
    // sequenceName="person_id_seq",
    // allocationSize = 1)    // умножаем на это число. указывать такой же, как в БД
    private int id;

    @Column(name="name")
    private String name;

    @ManyToOne  //owning side (владеющая сторона)
    @JoinColumn(name="director_id", referencedColumnName = "director_id")
    //в дочерней таблице поле director_id + в родительск таблице колонка называется director_id
    private Director owner;

    public Movie(){}

    public Movie(String name, Director owner) {
        this.name = name;
        this.owner = owner; //в конструкторе Movie есть Director
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Director getOwner() {
        return owner;
    }

    public void setOwner(Director owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", director='" + owner.getName() + '\'' +
                '}';
    }
}
