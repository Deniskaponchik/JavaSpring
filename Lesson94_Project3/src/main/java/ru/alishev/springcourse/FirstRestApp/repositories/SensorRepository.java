package ru.alishev.springcourse.FirstRestApp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Sensor;
import java.util.Optional;

/*
@Repository  //import org.springframework.stereotype.Repository;
public interface PeopleRepository extends JpaRepository<Person, Integer> {//даёт доступ к БД и работает с БД
    //Уже есть по умолчанию методы: findAll(), findOne() и т.д.
}*/
@Repository  //import org.springframework.stereotype.Repository;
public interface SensorRepository extends JpaRepository<Sensor, String> {
    Optional<Sensor> findByName(String name);//JpaRepository сам поймёт, что хотим найти сенсор по имени
}
