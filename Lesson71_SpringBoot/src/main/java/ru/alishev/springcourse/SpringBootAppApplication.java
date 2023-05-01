package ru.alishev.springcourse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //Главная аннотация, содержащая все остальные. Конфигурирует, сканирует рекурсивно компоненты. Содержит в себе: @ComponentScan, @Configuration,
public class SpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAppApplication.class, args);
	}

}
