package ru.alishev.springcourse.FirstRestApp.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

//DTO никак не связан с БД. Не помечен @Entity, @Table. @id не приходит от клиента. Поля @Column также нет
//DTO тесно связан ТОЛЬКО с контроллером
public class SensorDTO { //Описываем поля, которые будут приходить от клиента и будем клиенту отправлять

    //@id нет, т.к. оно не приходит от клиента. Приходит только название сернсора

    @NotEmpty(message="Name should not be empty") //@NotEmpty только для String
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String name;


    /*
    @Min(value=0, message="Age should be greater than zero")
    private int age;
    @Email
    @NotEmpty(message="Email should not be empty")
    private String email;
     */

    //Конструктора в DTO не нужно

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*
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
     */
}
