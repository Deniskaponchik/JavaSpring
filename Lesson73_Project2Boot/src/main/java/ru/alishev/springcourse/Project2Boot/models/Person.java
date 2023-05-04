package ru.alishev.springcourse.Project2Boot.models;
//Алишев. Требуется ИМЕННО 6 версия hibernate.validator
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;        //В пред. проектах было:: import javax.validation.constraints.Min;
import jakarta.validation.constraints.Size;       //В пред. проектах было: import javax.validation.constraints.Size;
//import jakarta.validation.constraints.NotEmpty; //В пред. проектах было:: import javax.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table(name="Person")  //import javax.persistence.Table;
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="full_name")
    //@NotEmpty(message = "Name should not be empty")
    @NotNull(message = "Title should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 chars")
    private String fullName;

    @Min(value = 1900, message = "Год рождения должен быть больше, чем 1900")
    @Column(name = "year_of_birth")
    private int yearOfBirth;


    /* в 69 проекте не понадобились
    @Column(name="email")
    @NotEmpty(message = "Name should not be empty")
    @Email(message = "email should be valid")
    private String email;

    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)               //import javax.persistence.*;   //DATE без миллисекунд(день, месяц, год)
    @DateTimeFormat(pattern = "dd/MM/yyyy")    //сможем парсить строку даты из формы. Если дата будет не в валидном формате, то возникнет НЕ user friendly ошибка
    private Date dateOfBirth;                  //import java.util.Date;

    @Column(name="created_at")
    @Temporal(TemporalType.TIMESTAMP)          //import javax.persistence.*;   //TIMESTAMP = с миллисекундами
    private Date createdAt; //дата будет ложиться НЕ в контроллере, а в Сервисе (бизнес-логика)

    @Enumerated(EnumType.STRING)
    //@Enumerated(EnumType.ORDINAL)  //ORDINAL каждому из перечислений выдаёт индекс (HAPPY = 0, SAD = 1 и т.д.) И сохран. в тбл
    private Mood mood;

    //@Transient    //НЕ ХРАНИМ В БД. Hibernate его не будет замечать
    //private int perimeter   //Удобно хранить вычисление на основании других полей. Проще и быстрее вычислить внутри JAVA
    */

        @OneToMany(mappedBy = "owner")
        private List<Book> books;

        public Person() {}   // Конструктор по умолчанию нужен для Spring
        public Person(String fullName, int yearOfBirth) {
            this.fullName = fullName;
            this.yearOfBirth = yearOfBirth;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getFullName() {        return fullName;    }
        public void setFullName(String fullName) {        this.fullName = fullName;    }
        public int getYearOfBirth() {        return yearOfBirth;    }
        public void setYearOfBirth(int yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
        }
        public List<Book> getBooks() {        return books;    }
        public void setBooks(List<Book> books) {        this.books = books;    }
        //public List<Item> getItems() {        return items;    }
        //public void setItems(List<Item> items) {        this.items = items;    }



        /* в 69 проекте не понадобились
        public String getEmail() {        return email;    }
        public void setEmail(String email) {        this.email = email;    }
        public Date getDateOfBirth() {        return dateOfBirth;    }
        public void setDateOfBirth(Date dateOfBirth) {        this.dateOfBirth = dateOfBirth;    }
        public Date getCreatedAt() {        return createdAt;    }
        public void setCreatedAt(Date createdAt) {        this.createdAt = createdAt;    }
        public Mood getMood() {        return mood;    }
        public void setMood(Mood mood) {        this.mood = mood;    }
         */
    @Override
    public String toString() { /*
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                '}';*/
        return getFullName();
    }

    /*Java 7+. Создаём для работы SET в PersonDAO
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(createdAt, person.createdAt) && mood == person.mood;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, dateOfBirth, createdAt, mood);
    }  */



    /* Default IntelijIdea equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (age != person.age) return false;
        if (!name.equals(person.name)) return false;
        if (!Objects.equals(email, person.email)) return false;
        if (!Objects.equals(dateOfBirth, person.dateOfBirth)) return false;
        if (!Objects.equals(createdAt, person.createdAt)) return false;
        return mood == person.mood;
    }
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (mood != null ? mood.hashCode() : 0);
        return result;
    } */



}



























