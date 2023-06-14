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
    private int id;

    @Column(name="value")
    @NotEmpty(message="Value should not be empty")
    @Min(value=-100, message="Value should be greater than -100")
    @Max(value=100, message="Value should be smaller than 100")
    private float value;

    @Column(name="raining")
    @NotEmpty(message="Raining should not be empty")
    private boolean raining;

    @ManyToOne
    @JoinColumn(name="sensor_id", referencedColumnName = "id")
    //@JoinColumn(name="sensor_id")
    private Sensor sensor;

    /*
    @Column(name = "name")
    @NotEmpty(message="Name should not be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String name;

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

    public Measurement() {}
    public Measurement(float value, boolean raining) {//Sensor не используем в конструкторе для Измерений
        this.value = value;
        this.raining = raining;
    }
    /*
    public Measurement(String name, int age) {
        this.name = name;
        this.age = age;
    }*/

    // JACKSON РАБОТАЕТ С ГЕТТЕРАМИ И СЕТТЕРАМИ
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public boolean isRaining() {
        return raining;
    }
    public void setRaining(boolean raining) {
        this.raining = raining;
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
