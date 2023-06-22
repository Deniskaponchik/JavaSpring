package ru.alishev.springcourse.FirstRestApp.dto;
import jakarta.validation.constraints.*;

//DTO никак не связан с БД. Не помечен @Entity, @Table. @id не приходит от клиента. Поля @Column также нет
//DTO тесно связан ТОЛЬКО с контроллером
public class MeasurementDTO { //Описываем поля, которые будут приходить от клиента и будем клиенту отправлять

    @NotNull //@NotEmpty ТОЛЬКО для String
    @Min(value=-100, message="Value should be greater than -100")
    @Max(value=100, message="Value should be smaller than 100")
    private Double value;//в тбл. поле double

    @NotNull //@NotEmpty не канает
    //@AssertFalse //raining - must be false
    //@AssertTrue  //raining - must be true
    private Boolean raining;

    @NotNull //@NotEmpty ТОЛЬКО для String
    private SensorDTO sensor;//в JSON -> "sensor":{"name":"Sensor name"}

    //Конструктора в DTO не нужно

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public Boolean isRaining() {
        return raining;
    }
    public void setRaining(boolean raining) {
        this.raining = raining;
    }
    public SensorDTO getSensor() {
        return sensor;
    }
    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

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
