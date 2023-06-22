package ru.alishev.springcourse.FirstRestApp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstRestApp.models.Measurement;

@Repository  //import org.springframework.stereotype.Repository;
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {//даёт доступ к БД и работает с БД
    //Уже есть по умолчанию методы: findAll(), findOne() и т.д.
}
