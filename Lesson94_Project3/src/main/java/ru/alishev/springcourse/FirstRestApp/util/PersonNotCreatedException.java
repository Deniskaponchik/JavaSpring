package ru.alishev.springcourse.FirstRestApp.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String msg){ //переопределяем дефолтный конструктор
        super(msg); //передали в RunTimeException
    }
}
