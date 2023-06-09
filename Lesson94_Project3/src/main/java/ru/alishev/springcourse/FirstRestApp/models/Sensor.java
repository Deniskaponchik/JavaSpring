package ru.alishev.springcourse.FirstRestApp.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor  implements Serializable{//сериализуем, потому что работаем не с числовыми ключами. обращаемся по name, а не id
    //Особенность Hibername. Если работаем НЕ с числовыми ключами, то нужно указать implements Serializable

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message="Name should not be empty") //@NotEmpty только для String
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String name;

    /* у Алишева нет такой аннотации
    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurements;
    */

    /*
    @Column(name = "age")
    @Min(value=0, message="Age should be greater than zero")
    private int age;

    @Column(name = "email")
    @Email
    @NotEmpty(message="Email should not be empty")
    private String email;

    @Column(name="created_at")
    private LocalDateTime createdAt; //timestamp in DB

    @Column(name="updated_at")
    private LocalDateTime updatedAt; //timestamp in DB

    @Column(name="created_who")
    @NotEmpty
    private String createdWho;
    */

    /*у Алишева НЕТ конструктора
    public Sensor() {    }
    public Sensor(String name) {
        this.name = name;
        //this.age = age;
    } */

    // JACKSON РАБОТАЕТ С ГЕТТЕРАМИ И СЕТТЕРАМИ. Если их не будет, т не сможет переводить в JSON
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /* у Алишева нет
    public List<Measurement> getMeasurements() {        return measurements;    }
    public void setMeasurements(List<Measurement> measurements) {        this.measurements = measurements;    }
    */

    /*
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getCreatedWho() {
        return createdWho;
    }
    public void setCreatedWho(String createdWho) {
        this.createdWho = createdWho;
    }
     */
}
