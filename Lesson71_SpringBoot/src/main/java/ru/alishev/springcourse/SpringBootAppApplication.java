package ru.alishev.springcourse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //Главная аннотация, содержащая все остальные. Конфигурирует, сканирует РЕКУРСИВНО компоненты. Содержит в себе: @ComponentScan, @Configuration,
//!!! ДОЛЖЕН находиться в корне проекта !!!
public class SpringBootAppApplication {

	public static void main(String[] args) {//Будет запускаться встроенный веб-сервер, а потом Spring Boot приложение

		SpringApplication.run(SpringBootAppApplication.class, args);
	}

}
