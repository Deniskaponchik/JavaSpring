package ru.alishev.springcourse.FirstRestApp.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="value")
    @NotNull //@NotEmpty ТОЛЬКО для String
    @Min(value=-100, message="Value should be greater than -100")
    @Max(value=100, message="Value should be smaller than 100")
    private Double value;
    //исп. НЕ примитивные типы, потому что обёртка может хранить NULL. Примитив же сохранил бы 0.0 при NULL. сможет срабатывать @NotEmpty

    @Column(name="raining")
    @NotNull //@NotEmpty ТОЛЬКО для String
    //@AssertFalse //raining - must be false
    //@AssertTrue  //raining - must be true
    private Boolean raining;

    @Column(name = "measurement_date_time")
    @NotNull //@NotEmpty ТОЛЬКО для String
    private LocalDateTime measurementDateTime;//в тбл timestamp

    @ManyToOne
    //@JoinColumn(name="sensor_id", referencedColumnName = "id")
    @JoinColumn(name="sensor", referencedColumnName="name")//name=поле в тек. тбл, ref name=поле в родительской зависимой тбл.
    private Sensor sensor;

    /*
    @Column(name = "name")
    @NotEmpty(message="Name should not be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "age")
    @Min(value=0, message="Age should be greater than zero")
    private int age;

    @Column(name="created_at")
    private LocalDateTime createdAt; //timestamp in DB

    @Column(name="updated_at")
    private LocalDateTime updatedAt; //timestamp in DB

    @Column(name="created_who")
    @NotEmpty
    private String createdWho;
     */

    /* у Алишева нет Конструктора
    public Measurement() {}
    public Measurement(Double value, Boolean raining) {//Sensor не используем в конструкторе для Измерений
        this.value = value;
        this.raining = raining;
    }*/

    // JACKSON РАБОТАЕТ С ГЕТТЕРАМИ И СЕТТЕРАМИ. Без них не сможет переводить в JSON
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public Boolean isRaining() {
        return raining;
    }
    public void setRaining(Boolean raining) {
        this.raining = raining;
    }
    public LocalDateTime getMeasurementDateTime() {
        return measurementDateTime;
    }
    public void setMeasurementDateTime(LocalDateTime measurementDateTime) {
        this.measurementDateTime = measurementDateTime;
    }
    public Sensor getSensor() {
        return sensor;
    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /*
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
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
