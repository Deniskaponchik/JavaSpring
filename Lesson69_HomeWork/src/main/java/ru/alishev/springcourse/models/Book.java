package ru.alishev.springcourse.models;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator

@Entity
@Table(name="Book")  //import javax.persistence.Table;
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 30, message = "Title should be between 2 and 30 chars")
    private String title;
    
    @Column(name="author")
    @NotEmpty(message = "Author should not be empty")
    @Size(min = 2, max = 100, message = "Author should be between 2 and 100 chars")
    private String author;

    @Column(name="year")
    @Min(value = 0, message = "Year should be greater than zero")
    private int year;

    @Column(name="taken_at")
    @Temporal(TemporalType.TIMESTAMP)      //import javax.persistence.*;   //TIMESTAMP = с миллисекундами
    private Date takenAt;                  //import java.util.Date;

    @Transient                //НЕ ХРАНИМ В БД. Hibernate его не будет замечать
    private boolean expired;  //Удобно хранить вычисление на основании других полей. Проще и быстрее вычислить внутри JAVA. по умолч. FALSE

    @ManyToOne
    @JoinColumn(name="person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {        return title;    }
    public void setTitle(String title) {        this.title = title;    }

    public String getAuthor() {        return author;    }
    public void setAuthor(String author) {        this.author = author;    }

    public int getYear() {        return year;    }

    public void setYear(int year) {        this.year = year;    }

    public Person getOwner() {        return owner;    }
    public void setOwner(Person owner) {        this.owner = owner;    }

    public Date getTakenAt() {        return takenAt;    }
    public void setTakenAt(Date takenAt) {        this.takenAt = takenAt;    }

    public boolean isExpired() {        return expired;    }
    public void setExpired(boolean expiration) {        this.expired = expired;    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", owner=" + owner +
                '}';
    }

    /* Реализовываю в Сервисе
    public boolean getExpiration(Date dateOfTaking){
        Date today = new Date();
        //int diffInDays = (int)( (newerDate.getTime() - olderDate.getTime()) / (1000 * 60 * 60 * 24) );
        int diffInDays = (int)( (today.getTime() - dateOfTaking.getTime()) / (1000 * 60 * 60 * 24) );
        return diffInDays > 10;
    }*/
}



























